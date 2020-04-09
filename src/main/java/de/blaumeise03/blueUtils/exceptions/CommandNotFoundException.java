/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.exceptions;

/**
 * This exception is called when the command was not found by Bukkit.
 *
 * @author Blaumeise03
 * @version 1.0
 */
public class CommandNotFoundException extends Exception {
    /**
     * Constructs a new CommandNotFoundException
     */
    public CommandNotFoundException() {
        super("The command was not found! Did you added it to the plugin.yml?");
    }

    /**
     * Constructs a new CommandNotFoundException
     *
     * @param message the detail message.
     */
    public CommandNotFoundException(String message) {
        super(message);
    }
}
