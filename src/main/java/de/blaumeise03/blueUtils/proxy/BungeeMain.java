/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.proxy;

import de.blaumeise03.blueUtils.crossServer.ServerState;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class BungeeMain extends Plugin {
    public static final String BLUE_UTILS_CHANNEL = "blueutils";
    static BungeeMain plugin;
    Set<ServerState> serverStates = new HashSet<>();

    @Override
    public void onEnable() {
        plugin = this;
        //ProxyServer.getInstance().getPluginManager().registerListener(this, new ChannelListener());
    }

    @Override
    public void onDisable() {

    }
}
