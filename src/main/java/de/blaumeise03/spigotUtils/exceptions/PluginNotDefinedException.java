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
