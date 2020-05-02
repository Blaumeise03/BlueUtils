/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import de.blaumeise03.blueUtils.crossServer.ServerBuffer;
import de.blaumeise03.blueUtils.exceptions.ConfigurationNotFoundException;
import de.blaumeise03.blueUtils.pluginCommands.MasterCommand;
import de.blaumeise03.blueUtils.simpleMenu.MenuListener;

/**
 * Plugin main-class of this library to get loaded by the server. Should not be used for anything or this library can get broken.
 *
 * @author Blaumeise03
 * @version 2.0
 * @implSpec DO NOT EXTEND BY THIS CLASS!! This class is only for internal reasons and could cause errors if used as super-class.
 * @since 1.8
 */
public class Plugin extends AdvancedPlugin {
    public static String serverName = null;
    static Configuration sqlConfig = null;
    private static Plugin plugin = null;
    private String serverState = "N/A";
    private ServerBuffer serverBuffer = null;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        setState("offline", "N/A");
        serverBuffer.close();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        getLogger().info("Spigot Utils by Blaumeise03. For Updates look at http://blaumeise03.de");
        getLogger().info("Information: Some older plugins could cause errors if they are for an older/newer version of this library so keep up-to-date.");
        getLogger().info("Adding commands...");
        MasterCommand.init();
        getLogger().info("Registering events...");
        registerEvent(new MenuListener());
        //getServer().getMessenger().registerOutgoingPluginChannel(this, "blueutils:proxy");
        //getServer().getMessenger().registerIncomingPluginChannel(this, "BlueUtils:Spigot", new BungeeReceiveListener());
        getLogger().info("Loading sql-config...");
        sqlConfig = new Configuration("sqlConfig.yml", this);
        try {
            sqlConfig.setup(true);
            sqlConfig.reload();
        } catch (ConfigurationNotFoundException e) {
            e.printStackTrace();
        }
        getLogger().info("Initializing SQL Database connection...");
        serverBuffer = new ServerBuffer(sqlConfig);
        serverName = sqlConfig.getString("serverName");
        if (serverName == null) {
            serverName = "SpigotServer";
        }
        setState("online", "N/A");
        getLogger().info("Complete!");
    }

    public void setState(String newState, String newExtra) {
        if (serverName == null) return;
        serverState = newState;
        /*for (Player p : getServer().getOnlinePlayers()) {
            try (
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(stream)
            ) {
                out.writeUTF("setStatus");
                out.writeUTF(serverName);
                out.writeUTF(serverState);
                p.sendPluginMessage(this, "blueutils:proxy", stream.toByteArray());
                getLogger().info("Send ServerState-update!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }*/

        serverBuffer.setState(serverName, newState, newExtra);
    }

    public String getServerState() {
        return serverState;
    }

    public ServerBuffer getServerBuffer() {
        return serverBuffer;
    }
}
