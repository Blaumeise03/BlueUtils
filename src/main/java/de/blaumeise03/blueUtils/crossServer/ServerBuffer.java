/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.crossServer;

import de.blaumeise03.blueUtils.Configuration;
import de.blaumeise03.blueUtils.Plugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerBuffer {
    private final String url;
    private final String host;
    private final String port;
    private final String user;
    private final String password;
    private final String serverTableName = "serverStates";
    private final List<ServerState> states = new ArrayList<>();
    private Connection connection = null;
    private PreparedStatement insertStm = null;
    private PreparedStatement selectStm = null;

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
            //Plugin.getPlugin().getLogger().warning("Host: " + host + " Port: " + port + " User: " + user + " No, I won't print the password...");
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, user, password);
            tryCreateTable();
            insertStm = connection.prepareStatement("REPLACE INTO ? (server, state, extra) VALUES (?, '?, ?);");
            selectStm = connection.prepareStatement("SELECT * FROM ?");
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void tryCreateTable() throws SQLException {
        try (Statement stm = connection.createStatement()) {
            stm.execute("CREATE TABLE IF NOT EXISTS " + serverTableName + "(\n" +
                    "server VARCHAR(20) NOT NULL,\n" +
                    "state TEXT(200),\n" +
                    "extra TEXT(300), \n" +
                    "PRIMARY KEY (server));");
        }
    }

    public void refreshBuffer() {
        if (connection == null || selectStm == null) return;
        try {
            selectStm.setString(1, serverTableName);
        } catch (SQLException e) {
            e.printStackTrace();
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
        }
    }

    public void setState(String server, String state, String extra) {
        if (connection == null) return;
        for (ServerState serverState : states) {
            if (serverState.getServer().equalsIgnoreCase(server)) {
                serverState.state = state;
                serverState.extra = extra;
                server = serverState.getServer();
                break;
            }
        }
        /*try (Statement stmt = connection.createStatement()) {
            //stmt.execute("INSERT OR REPLACE INTO " + serverTableName + " (server, state, extra) VALUES (\"" + server + "\", \"" + state + "\", \"" + extra + "\");");
            /*stmt.execute(
                    "IF EXISTS(SELECT * FROM " + serverTableName + " WHERE server='" + server + "') THEN\n" +
                            "  UPDATE " + serverTableName + " SET state='" + state + "', extra='" + extra + "' WHERE server='" + server + "'\n" +
                            "ELSE\n" +
                            "  INSERT INTO " + serverTableName + " (server, state, extra) " +
                            "VALUES ('" + server + "', '" + state + "', '" + extra + "')\nEND IF;"
            );/
            stmt.execute(
                    "REPLACE INTO " + serverTableName + " (server, state, extra) VALUES ('" + server + "', '" + state + "', '" + extra + "');"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            insertStm.setString(1, serverTableName);
            insertStm.setString(2, server);
            insertStm.setString(3, state);
            insertStm.setString(4, extra);
            insertStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            insertStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ServerState> getStates() {
        return states;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getServerTableName() {
        return serverTableName;
    }
}
