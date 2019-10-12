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
