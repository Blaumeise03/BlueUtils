/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.crossServer;

public class ServerState {
    String server;
    String state;
    String extra;

    public ServerState(String server, String state, String extra) {
        this.server = server;
        this.state = state;
        this.extra = extra;
    }

    public ServerState(String server, String state) {
        this.state = state;
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
