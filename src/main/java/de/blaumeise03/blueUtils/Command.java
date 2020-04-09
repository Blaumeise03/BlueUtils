/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * This Class simplifies the Spigot-Commands
 *
 * @author Blaumeise03
 * @version 2.0
 * @since 1.8
 */
abstract public class Command {
    private String label;
    private List<String> alias = new ArrayList<>();
    private Permission permission = null;
    private String help;
    private boolean onlyPlayer = false;
    private boolean thirdExecutable = false;
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
     * @deprecated Permission is not needed if defined in plugin.yml
     */
    @Deprecated
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
     * Constructor for creating a new command.
     *
     * @param label      The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help       The help-text for the command
     * @param onlyPlayer If true only players may execute the command.
     */
    public Command(CommandHandler handler, String label, String help, boolean onlyPlayer) {
        this.label = label;
        this.help = help;
        this.onlyPlayer = onlyPlayer;
        this.handler = handler;
        handler.addCommand(this);
        //handler.registerCommand(label);
    }

    /**
     * Constructor for creating a new command.
     *
     * @param label           The prefix of the command, e.g. /prefix arg0 arg1 arg2...
     * @param help            The help-text for the command
     * @param onlyPlayer      If true only players may execute the command.
     * @param thirdExecutable If true players with permission are able to execute this command for another player: /label [targetPlayer] [otherArguments...]
     */
    public Command(CommandHandler handler, String label, String help, boolean onlyPlayer, boolean thirdExecutable) {
        this.label = label;
        this.help = help;
        this.onlyPlayer = onlyPlayer;
        this.handler = handler;
        this.thirdExecutable = thirdExecutable;
        handler.addCommand(this);
        //handler.registerCommand(label);
    }

    /**
     * This method will be executed if a player execute the command.
     *
     * @param args             The arguments from the command (if the player has entered some).
     * @param sender           The CommandSender of the command. If 'onlyPlayer' form the constructor is true this will be a player.
     * @param isPlayer         If the sender is a Player.
     * @param isThirdExecution If the command is executed for another player by the {@code realSender}
     * @param realSender       The real sender of the command if it is executed by another player via third-execution
     */
    public abstract void onCommand(String[] args, CommandSender sender, boolean isPlayer, boolean isThirdExecution, CommandSender realSender);

    /**
     * Defines what should happen if the player has no permissions.
     *
     * @param sender  The CommandSender who tried to execute the command.
     * @param command The command executed
     */
    protected void onNoPermission(CommandSender sender, org.bukkit.command.@NotNull Command command) {
        onNoPermission(sender);
    }

    /**
     * Defines what should happen if the player has no permissions.
     *
     * @param sender The CommandSender who tried to execute the command.
     */
    protected void onNoPermission(CommandSender sender) {
        sender.sendMessage("§4Dazu hast du keine Rechte!");
    }

    /**
     * Checks if the command wich was executed by the player equals. WARING: Aliases doesn't work!
     *
     * @param c The command wich was executed
     * @return Returns true if the label equals the command.
     */
    public boolean isCommand(final org.bukkit.command.Command c) {
        return this.getLabel().equalsIgnoreCase(c.getName());
    }

    /**
     * Checks if the player has permission for executing the command.
     *
     * @param player The player who tries to execute the command.
     * @return Returns true if the player has the permission.
     */
    protected boolean hasPermission(Player player) {
        return permission != null && player.hasPermission(permission);
    }

    /**
     * Method for checking if the command equals the command from the player. It also checks if the executor must be a player.
     *
     * @param command The {@code org.bukkit.command.Command}-Command
     * @param sender  Equals the CommandSender of the onCommand method.
     * @param label   Equals the label of the onCommand method.
     * @param args    Equals the args of the onCommand method.
     */
    public void run(@NotNull org.bukkit.command.Command command, CommandSender sender, String label, String[] args) {
        if (isCommand(command)) {
            CommandSender realSender = null;
            boolean third = false;
            if (thirdExecutable && args.length >= 1) {
                String a = args[0];
                Player p = Bukkit.getPlayer(a);
                if (p != null) {
                    if (sender instanceof Player) {
                        if (!sender.hasPermission("spigotUtils.thirdExecution")) {
                            sender.sendMessage("§cDu darfst Befehle nicht für andere Spieler ausführen!");
                            return;
                        }
                    }
                    realSender = sender;
                    sender = p;
                    third = true;
                    String[] oldArgs = args;
                    args = new String[oldArgs.length - 1];
                    for (int i = 1, argsLength = oldArgs.length; i < argsLength; i++) {
                        args[i - 1] = oldArgs[i];
                    }
                }
            }
            if (!third && sender instanceof Player) {
                if (hasPermission((Player) sender) || command.getPermission() != null && sender.hasPermission(command.getPermission())) {
                    handler.plugin.getLogger().info("The player " + sender.getName() + " executed command " + command.getName() + ". "
                            + (third ? "The command was originally executed by " + realSender.getName() : ""));
                    if (third)
                        realSender.sendMessage(ChatColor.GREEN + "Befehl wurde bei " + sender.getName() + " ausgeführt!");
                    onCommand(args, sender, true, third, realSender);
                } else {
                    onNoPermission(sender, command);
                }
            } else {
                if (realSender instanceof Player) {
                    if (hasPermission((Player) realSender) || command.getPermission() != null && realSender.hasPermission(command.getPermission())) {
                        handler.plugin.getLogger().info("The player " + sender.getName() + " executed command " + command.getName() + ". "
                                + (third ? "The command was originally executed by " + realSender.getName() : ""));
                        if (third)
                            realSender.sendMessage(ChatColor.GREEN + "Befehl wurde bei " + sender.getName() + " ausgeführt!");
                        onCommand(args, sender, true, third, realSender);
                    }
                } else if (!onlyPlayer) {
                    onCommand(args, sender, false, third, realSender);
                    handler.plugin.getLogger().info(sender.getName() + " executed command " + command.getName() + ". "
                            + (third ? "The command was originally executed by " + realSender.getName() : ""));
                } else sender.sendMessage("You must be a Player to execute this Command!");
            }
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