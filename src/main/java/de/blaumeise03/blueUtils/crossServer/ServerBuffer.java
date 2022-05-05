/*
 * Copyright (c) 2022 Blaumeise03
 */

package de.blaumeise03.blueUtils.crossServer;

import de.blaumeise03.blueUtils.Configuration;
import de.blaumeise03.blueUtils.Plugin;
import de.blaumeise03.blueUtils.utils.Timer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerBuffer {
    private static final String serverTableName = "serverStates";
    private boolean isDatabaseIsActive = true;
    private final String url;
    private final String host;
    private final String port;
    private final String user;
    private final String password;
    private final List<ServerState> states = new ArrayList<>();
    private Connection connection = null;
    private PreparedStatement insertStm = null;
    private PreparedStatement selectStm = null;
    private final Timer timer = new Timer("BlueUtils ServerBuffer refresh timings", "logs/latestBlueUtilsSQLTimings.txt");

    public ServerBuffer(Configuration config) {
        this(
                config.getString("database.mysql.host"),
                config.getString("database.mysql.port"),
                config.getString("database.mysql.user"),
                config.getString("database.mysql.password"),
                config.getString("database.mysql.databaseName")
        );
    }

    public ServerBuffer(String host, String port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        this.user = user;
        this.password = password;
        if (host == null || port == null || user == null || password == null || database == null || user.equalsIgnoreCase("INSERT USER NAME") || password.equalsIgnoreCase("INSERT PASSWORD")) {
            Plugin.getPlugin().getLogger().severe("Failed loading sql-config! At least on information is missing!");
            isDatabaseIsActive = false;
            //Plugin.getPlugin().getLogger().warning("Host: " + host + " Port: " + port + " User: " + user + " No, I won't print the password...");
        }
        if (!isDatabaseIsActive) return;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);
            tryCreateTable();
            insertStm = connection.prepareStatement("REPLACE INTO " + serverTableName + " (server, state, extra) VALUES (?, ?, ?);");
            selectStm = connection.prepareStatement("SELECT * FROM " + serverTableName);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public synchronized void tryCreateTable() throws SQLException {
        try (Statement stm = connection.createStatement()) {
            stm.execute("CREATE TABLE IF NOT EXISTS " + serverTableName + "(\n" +
                    "server VARCHAR(20) NOT NULL,\n" +
                    "state TEXT(200),\n" +
                    "extra TEXT(300), \n" +
                    "PRIMARY KEY (server));");
        }
    }

    public synchronized void refreshBuffer() {
        //if(timer.isRunning()) timer.reset();
        //timer.start();
        if (!isDatabaseIsActive) return;
        try {
            if (connection == null || selectStm == null || connection.isClosed() || selectStm.isClosed()) {
                refreshConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*try {
            selectStm.setString(1, serverTableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            if (selectStm == null || connection == null || selectStm.isClosed() || connection.isClosed()) {
                Plugin.getPlugin().getLogger().severe("Can't load state! SQL-Connection is not available!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        try (
                //Statement stmt = connection.createStatement();
                //ResultSet res = stmt.executeQuery("SELECT * FROM " + serverTableName)
                ResultSet res = selectStm.executeQuery()
        ) {
            states.clear();
            while (res.next()) {
                String server = res.getString("server");
                String sState = res.getString("state");
                String sExtra = res.getString("extra");
                boolean exits = false;
                for (ServerState state : states) {
                    if (state.server.equalsIgnoreCase(server)) {
                        exits = true;
                        state.state = sState;
                        state.extra = sExtra;
                    }
                }
                if (!exits) {
                    states.add(new ServerState(server, sState, sExtra));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        //timer.stop();
    }

    private synchronized void refreshConnection() {
        if (!isDatabaseIsActive) return;
        Plugin.getPlugin().getLogger().info("Refreshing SQL-Connection...");
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(2))
                connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        try {
            if (insertStm == null || insertStm.isClosed())
                insertStm = connection.prepareStatement("REPLACE INTO " + serverTableName + " (server, state, extra) VALUES (?, ?, ?);");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        try {
            if (selectStm == null || insertStm.isClosed())
                selectStm = connection.prepareStatement("SELECT * FROM " + serverTableName);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        Plugin.getPlugin().getLogger().info("Complete!");
    }

    public synchronized void setState(String server, String state, String extra) {
        this.setState(server, state, extra, true);
    }

    public synchronized void setState(String server, String state, String extra, boolean retry) {
        if (!isDatabaseIsActive) return;
        try {
            if (connection == null || insertStm == null || selectStm == null || connection.isClosed() || insertStm.isClosed() || selectStm.isClosed()) {
                refreshConnection();
            }
        } catch (SQLException e) {
            Plugin.getPlugin().getLogger().info("JDBC threw an exception: ");
            e.printStackTrace();
            refreshConnection();
        }
        for (ServerState serverState : states) {
            if (serverState.getServer().equalsIgnoreCase(server)) {
                serverState.state = state;
                serverState.extra = extra;
                server = serverState.getServer();
                break;
            }
        }

        try {
            //insertStm.setString(1, serverTableName);
            if (insertStm == null || insertStm.isClosed()) {
                Plugin.getPlugin().getLogger().severe("Can't update state! Statement ist null or is closed!");
                return;
            }
            insertStm.setString(1, server);
            insertStm.setString(2, state);
            insertStm.setString(3, extra);
            insertStm.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            Plugin.getPlugin().getLogger().info("JDBC threw an exception: ");
            e.printStackTrace();
            refreshConnection();
            if (retry) {
                Plugin.getPlugin().getLogger().info("JDBC threw an exception, reattemping...");
                setState(server, state, extra, false);
            }
        }
    }

    public synchronized void close() {
        if (!isDatabaseIsActive) return;
        Plugin.getPlugin().getLogger().info("Closing SQL...");
        try {
            if (insertStm != null)
                insertStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (selectStm != null)
                selectStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void saveLog() {
        try {
            timer.logTimings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<ServerState> getStates() {
        return new ArrayList<>(states);
    }

    public synchronized Connection getConnection() {
        return connection;
    }

    public synchronized String getServerTableName() {
        return serverTableName;
    }
}
