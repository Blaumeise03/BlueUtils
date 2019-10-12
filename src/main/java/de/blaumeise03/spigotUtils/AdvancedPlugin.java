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

package de.blaumeise03.spigotUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class AdvancedPlugin extends JavaPlugin {
    private static AdvancedPlugin plugin;
    private PluginManager pluginManager;

    /**
     * Getter for the plugin field.
     *
     * @return the plugin.
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        pluginManager = Bukkit.getPluginManager();
    }

    /**
     * Use to register new events.
     *
     * @param listener the event-class.
     */
    private void registerEvent(Listener listener) {
        pluginManager.registerEvents(listener, this);
    }

    /**
     * Executes the commands automatically.
     *
     * @param sender  the CommandSender.
     * @param command the org.bukkit.Command.
     * @param label   the label of the command.
     * @param args    the arguments of the executed command.
     * @return always true.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        de.blaumeise03.spigotUtils.Command.executeCommand(args, sender, label);
        return true;
    }

    public void warn(String message) {
        getLogger().log(Level.WARNING, message);
    }

    public void info(String message) {
        getLogger().log(Level.INFO, message);
    }
}
