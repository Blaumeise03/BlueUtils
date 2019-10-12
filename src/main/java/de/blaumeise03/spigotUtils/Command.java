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

import de.blaumeise03.spigotUtils.exceptions.CommandNotFoundException;
import de.blaumeise03.spigotUtils.exceptions.PluginNotDefinedException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;


/**
 * This Class simplifies the Spigot-Commands
 *
 * @author Blaumeise03
 * @version 1.0
 * @since 1.8
 */
abstract public class Command implements CommandExecutor {
    private static List<Command> commands = new ArrayList<>();
    private static JavaPlugin plugin;

    private String label;
    private List<String> alias = new ArrayList<>();
    private Permission permission;
    private String help;
    private boolean onlyPlayer = false;

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix args[0] args[1] args[2]...
     * @param help       The help-text for the command.
     * @param permission The Bukkit-permission for the command.
     * @throws PluginNotDefinedException is thrown when the plugin is null (if the
     *                                   {@link de.blaumeise03.spigotUtils.Command#setup(JavaPlugin) setup(JavaPlugin)} was not executed).
     * @throws CommandNotFoundException  is thrown when the command was not found, e.g. when the command is not specified in the plugin.yml
     */
    public Command(String label, String help, Permission permission) throws PluginNotDefinedException, CommandNotFoundException {
        this.label = label;
        this.help = help;
        this.permission = permission;
        commands.add(this);
        registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param alias      A list with aliases for the Command
     * @throws PluginNotDefinedException is thrown when the plugin is null (if the
     *                                   {@link de.blaumeise03.spigotUtils.Command#setup(JavaPlugin) setup(JavaPlugin)} was not executed).
     * @throws CommandNotFoundException  is thrown when the command was not found, e.g. when the command is not specified in the plugin.yml
     * @deprecated The alias-feature does not work!
     */
    @Deprecated
    public Command(String label, String help, Permission permission, List<String> alias) throws PluginNotDefinedException, CommandNotFoundException {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.alias = alias;
        commands.add(this);
        registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param alias      A list with aliases for the Command
     * @param onlyPlayer If true only players may execute the command.
     * @throws PluginNotDefinedException is thrown when the plugin is null (if the
     *                                   {@link de.blaumeise03.spigotUtils.Command#setup(JavaPlugin) setup(JavaPlugin)} was not executed).
     * @throws CommandNotFoundException  is thrown when the command was not found, e.g. when the command is not specified in the plugin.yml
     * @deprecated The alias-feature does not work!
     */
    @Deprecated
    public Command(String label, String help, Permission permission, List<String> alias, boolean onlyPlayer) throws PluginNotDefinedException, CommandNotFoundException {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.alias = alias;
        this.onlyPlayer = onlyPlayer;
        commands.add(this);
        registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param onlyPlayer If true only players may execute the command.
     * @throws PluginNotDefinedException is thrown when the plugin is null (if the
     *                                   {@link de.blaumeise03.spigotUtils.Command#setup(JavaPlugin) setup(JavaPlugin)} was not executed).
     * @throws CommandNotFoundException  is thrown when the command was not found, e.g. when the command is not specified in the plugin.yml
     */
    public Command(String label, String help, Permission permission, boolean onlyPlayer) throws PluginNotDefinedException, CommandNotFoundException {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
        commands.add(this);
        registerCommand(label);
    }

    /**
     * Method for setting the plugin (for registering the commands).
     * <b>Note:</b> just add 'Command.setup(this)' in you plugin.yml. It must be before you add the commands.
     *
     * @param plugin the JavaPlugin.
     */
    public static void setup(JavaPlugin plugin) {
        Command.plugin = plugin;
    }

    /**
     * Method for registering a new command.
     *
     * @param command the label of the command.
     * @throws PluginNotDefinedException is thrown when the plugin is null (if the
     *                                   {@link de.blaumeise03.spigotUtils.Command#setup(JavaPlugin) setup(JavaPlugin)} was not executed).
     * @throws CommandNotFoundException  is thrown when the command was not found, e.g. when the command is not specified in the plugin.yml
     */
    private static void registerCommand(String command) throws PluginNotDefinedException, CommandNotFoundException {
        if (plugin == null) {
            throw new PluginNotDefinedException();
        } else {
            PluginCommand c = plugin.getCommand(command);
            if (c == null) {
                throw new CommandNotFoundException();
            }
            c.setExecutor(new CommandHandler());
        }
    }

    /**
     * This method will be executed if a command is executed.
     *
     * @param args   The arguments of the command (/label args[]).
     * @param sender The CommandSender (should be self-explained).
     * @param label  The command wich should be executed.
     * @return Returns a boolean if the command was found (NOT if the command was executed successfully).
     */
    static boolean executeCommand(String[] args, CommandSender sender, String label) {
        for (Command command : commands) {
            if (command.isCommand(label)) {
                command.run(sender, label, args);
                return true;
            }
        }
        return false;
    }

    /**
     * This method will be executed if a player execute the command.
     *
     * @param args   The arguments from the command (if the player has entered some).
     * @param sender The CommandSender of the command. If 'onlyPlayer' form the constructor is true this will be a player.
     */
    public abstract void onCommand(String[] args, CommandSender sender);

    /**
     * Defines what should happen if the player has no permissions.
     *
     * @param sender The CommandSender who tried to execute the command.
     */
    public void onNoPermission(CommandSender sender) {
        sender.sendMessage("§4Dazu hast du keine Rechte!");
    }

    /**
     * Checks if the command wich was executed by the player equals. WARING: Aliases doesn't work!
     *
     * @param label The label of the command the player entered.
     * @return Returns true if the label equals the command.
     */
    private boolean isCommand(final String label) {
        final boolean[] al = {false};
        alias.forEach(s -> al[0] = (s.equalsIgnoreCase(label) || al[0]));
        return (label.equalsIgnoreCase(this.label) || al[0]);
    }

    /**
     * Checks if the player has permission for executing the command.
     *
     * @param player The player who tries to execute the command.
     * @return Returns true if the player has the permission.
     */
    private boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }

    /**
     * Method for checking if the command equals the command from the player. It also checks if the executor must be a player.
     *
     * @param sender Equals the CommandSender of the onCommand method.
     * @param label  Equals the label of the onCommand method.
     * @param args   Equals the args of the onCommand method.
     */
    private void run(CommandSender sender, String label, String[] args) {
        if (isCommand(label)) {
            if (sender instanceof Player) {
                if (hasPermission((Player) sender)) {
                    onCommand(args, sender);
                } else onNoPermission(sender);
            } else if (!onlyPlayer) {
                onCommand(args, sender);
            } else sender.sendMessage("You must be a Player to execute this Command!");
        }
    }

    /**
     * Getter for the command-label.
     *
     * @return The label of the command.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Getter for the required permission.
     *
     * @return The permission for the command.
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Getter for the help-text of the comand.
     *
     * @return The help-text.
     */
    public String getHelp() {
        return help;
    }

    /**
     * Used to add an alias.
     *
     * @param alias The alias to add.
     * @deprecated Aliases doesn't work.
     */
    public void addAlias(String alias) {
        this.alias.add(alias);
    }
}