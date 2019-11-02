/*
 * Copyright (c) 2019 Blaumeise03
 */

package de.blaumeise03.spigotUtils.exceptions;

/**
 * This exception is called when the plugin is not set for the 'Command'-class.
 *
 * @author Blaumeise03
 * @version 1.0
 */
public class PluginNotDefinedException extends Exception {

    /**
     * Constructs a new PluginNotFoundException.
     */
    public PluginNotDefinedException() {
        super("Plugin was not defined when trying to register a new command.");
    }

    /**
     * Constructs a new PluginNotFoundException.
     *
     * @param message the detail message.
     */
    public PluginNotDefinedException(String message) {
        super(message);
    }
}
