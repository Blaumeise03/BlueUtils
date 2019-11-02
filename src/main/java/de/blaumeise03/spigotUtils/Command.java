/*
 * Copyright (c) 2019 Blaumeise03
 */

package de.blaumeise03.spigotUtils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;


/**
 * This Class simplifies the Spigot-Commands
 *
 * @author Blaumeise03
 * @version 1.0
 * @since 1.8
 */
abstract public class Command {
    private String label;
    private List<String> alias = new ArrayList<>();
    private Permission permission;
    private String help;
    private boolean onlyPlayer = false;
    private CommandHandler handler;

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix args[0] args[1] args[2]...
     * @param help       The help-text for the command.
     * @param permission The Bukkit-permission for the command.
     */
    public Command(CommandHandler handler, String label, String help, Permission permission) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.handler = handler;
        handler.addCommand(this);
        //handler.registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param alias      A list with aliases for the Command
     * @deprecated The alias-feature does not work!
     */
    @Deprecated
    public Command(CommandHandler handler, String label, String help, Permission permission, List<String> alias) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.alias = alias;
        this.handler = handler;
        handler.addCommand(this);
        //handler.registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param alias      A list with aliases for the Command
     * @param onlyPlayer If true only players may execute the command.
     * @deprecated The alias-feature does not work!
     */
    @Deprecated
    public Command(CommandHandler handler, String label, String help, Permission permission, List<String> alias, boolean onlyPlayer) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.alias = alias;
        this.onlyPlayer = onlyPlayer;
        this.handler = handler;
        handler.addCommand(this);
        //handler.registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param permission The Bukkit-permission for the command.
     * @param onlyPlayer If true only players may execute the command.
     */
    public Command(CommandHandler handler, String label, String help, Permission permission, boolean onlyPlayer) {
        this.label = label;
        this.help = help;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
        this.handler = handler;
        handler.addCommand(this);
        //handler.registerCommand(label);
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
    public boolean isCommand(final String label) {
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
    public void run(CommandSender sender, String label, String[] args) {
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