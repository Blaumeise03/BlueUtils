/*
 * Copyright (c) 2019 Blaumeise03
 */

package de.blaumeise03.spigotUtils.exceptions;

import java.io.File;
import java.io.FileNotFoundException;

public class ConfigurationNotFoundException extends FileNotFoundException {
    private File file = null;

    /**
     * Constructs a <code>FileNotFoundException</code> with
     * <code>Configuration file not found! It must be created
     * before it can be accessed.</code> as its error detail message.
     */
    public ConfigurationNotFoundException() {
        super("Configuration file not found! It must be created before it can be accessed.");
    }

    /**
     * Constructs a <code>FileNotFoundException</code> with the
     * specified detail message. The string <code>s</code> can be
     * retrieved later by the
     * <code>{@link Throwable#getMessage}</code>
     * method of class <code>java.lang.Throwable</code>.
     *
     * @param s the detail message.
     */
    public ConfigurationNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>FileNotFoundException</code> with
     * <code>Configuration file not found! It must be created
     * before it can be accessed.</code> as its error detail message.
     * The <code>file</code> is the missing file and can be
     * retrieved later by {@link ConfigurationNotFoundException#getFile()}
     * method.
     *
     * @param file the missing file.
     */
    public ConfigurationNotFoundException(File file) {
        super("Configuration file not found! It must be created before it can be accessed.");
        this.file = file;
    }

    /**
     * Constructs a <code>FileNotFoundException</code> with the
     * specified detail message. The string <code>s</code> can be
     * retrieved later by the
     * <code>{@link Throwable#getMessage}</code>
     * method of class <code>java.lang.Throwable</code>.
     * The <code>file</code> is the missing file and can be
     * retrieved later by {@link ConfigurationNotFoundException#getFile()}
     * method.
     *
     * @param s    the detail message.
     * @param file the missing file.
     */
    public ConfigurationNotFoundException(String s, File file) {
        super(s);
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
