/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.proxy;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static de.blaumeise03.blueUtils.proxy.BungeeMain.BLUE_UTILS_CHANNEL;

public class ChannelListener implements Listener {

    @EventHandler
    public void receive(PluginMessageEvent e) {
        if (e.getTag().equalsIgnoreCase(BLUE_UTILS_CHANNEL)) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            try {
                String channel = in.readUTF();
                if (channel.equals("tpPlayerToPlayer")) {
                    ServerInfo server = ProxyServer.getInstance().getPlayer(e.getReceiver().toString()).getServer().getInfo();
                    String pTarget = in.readUTF();
                    String pTo = in.readUTF();
                    ProxiedPlayer target = ProxyServer.getInstance().getPlayer(pTarget);
                    ProxiedPlayer executer = ProxyServer.getInstance().getPlayer(e.getReceiver().toString());
                    if (target == null) {
                        TextComponent text = new TextComponent("Couldn't teleport player \"" + pTarget + "\" to \"" + pTo + "\"! The player \"" + pTarget + "\" was not found on any server!");
                        executer.sendMessage(ChatMessageType.CHAT, text);
                        return;
                    }
                    ProxiedPlayer to = ProxyServer.getInstance().getPlayer(pTo);
                    if (to == null) {
                        TextComponent text = new TextComponent("Couldn't teleport player \"" + pTarget + "\" to \"" + pTo + "\"! The player \"" + pTo + "\" was not found on any server!");
                        executer.sendMessage(ChatMessageType.CHAT, text);
                        return;
                    }
                    
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
