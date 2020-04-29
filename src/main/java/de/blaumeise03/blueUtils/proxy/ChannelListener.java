/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.proxy;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.blaumeise03.blueUtils.crossServer.ServerState;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static de.blaumeise03.blueUtils.proxy.BungeeMain.BLUE_UTILS_CHANNEL;

@Deprecated
public class ChannelListener implements Listener {

    @EventHandler
    public void receive(PluginMessageEvent e) {
        if (e.getTag().equalsIgnoreCase(BLUE_UTILS_CHANNEL + ":proxy")) {
            try {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
                String channel = in.readUTF(); // channel we delivered
                BungeeMain.plugin.getLogger().info("Received message: " + channel);
                if (channel.equals("setStatus")) {
                    //ServerInfo server = BungeeCord.getInstance().getPlayer(e.getReceiver().toString()).getServer().getInfo();
                    String serv = in.readUTF(); // the inputstring
                    String newState = in.readUTF();
                    boolean exist = false;
                    for (ServerState state : BungeeMain.plugin.serverStates) {
                        if (state.getServer().equalsIgnoreCase(serv)) {
                            state.setState(newState);
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        BungeeMain.plugin.getLogger().info("Adding new Server!");
                        BungeeMain.plugin.serverStates.add(new ServerState(serv, newState));
                    }
                    BungeeMain.plugin.getLogger().info("Server: " + serv + " State: " + newState);
                } else if (channel.equalsIgnoreCase("queryStatus")) {
                    if (!(e.getSender() instanceof Server)) return;
                    String server = in.readUTF();
                    for (ServerState state : BungeeMain.plugin.serverStates) {
                        if (state.getServer().equalsIgnoreCase(server)) {
                            ByteArrayDataOutput out = ByteStreams.newDataOutput();
                            out.writeUTF("statusResult");
                            out.writeUTF(state.getServer());
                            out.writeUTF(state.getState());
                            ((Server) e.getSender()).sendData(BLUE_UTILS_CHANNEL + ":spigot", out.toByteArray());
                            break;
                        }
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
