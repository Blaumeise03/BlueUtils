/*
 *     Copyright (C) 2019  Blaumeise03 - bluegame61@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
