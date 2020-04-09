/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import de.blaumeise03.blueUtils.exceptions.CommandNotFoundException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Handler for the {@link Command Commands}
 *
 * @author Blaumeise03
 * @version 2.0
 * @implSpec Do not instantiate your own, simply use the one from {@link AdvancedPlugin#getHandler()}
 * @since 1.8
 */
public class CommandHandler implements CommandExecutor {
    private List<Command> commands = new ArrayList<>();
    JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * This method will be executed if a command is executed.
     *
     * @param args   The arguments of the command (/label args[]).
     * @param sender The CommandSender (should be self-explained).
     * @param label  The command wich should be executed.
     * @return Returns a boolean if the command was found (NOT if the command was executed successfully).
     */
    public boolean executeCommand(org.bukkit.command.Command command, String[] args, CommandSender sender, String label) {
        for (Command c : commands) {
            if (c.isCommand(command)) {
                c.run(command, sender, label, args);
                return true;
            }
        }
        return false;
    }

    /**
     * Method for registering a new command.
     *
     * @param command the label of the command.
     * @throws CommandNotFoundException is thrown when the command was not found, e.g. when the command is not specified in the plugin.yml
     * @deprecated unused.
     */
    public void registerCommand(String command) throws CommandNotFoundException {
        PluginCommand c = plugin.getCommand(command);
        if (c == null) {
            throw new CommandNotFoundException();
        }
        c.setExecutor(this);
    }

    public void addCommand(Command c) {
        commands.add(c);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        executeCommand(command, strings, commandSender, s);
        return false;
    }
}
