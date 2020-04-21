/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.command;

import de.blaumeise03.blueUtils.exceptions.CommandNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CommandHandler implements TabExecutor {
    Set<Command> commands = new HashSet<>();

    public void addCommand(Command command) throws CommandNotFoundException {
        commands.add(command);
        PluginCommand c = Bukkit.getPluginCommand(command.label);
        if (c == null) {
            throw new CommandNotFoundException("Command " + command.label + " not found and could not get registered!");
        }
        c.setExecutor(this);
        c.setTabCompleter(this);
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        for (Command c : commands) {
            if (command.getName().equalsIgnoreCase(c.getLabel())) {
                boolean third = false;
                CommandSender originalSender = sender;
                if (c.thirdExecutable && args.length > 0 && (!(sender instanceof Player) || sender.hasPermission("blueUtils.thirdExecution"))) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p != null) {
                        third = true;
                        sender = p;
                        args = Arrays.copyOfRange(args, 1, args.length);
                    }
                }
                Command current = c;
                String[] nArgs = args;
                int i = 0;
                boolean end = false;
                while (!end) {
                    end = true;
                    for (Command subC : current.subCommands) {
                        //System.out.println(subC.label + "\"" + args[0] +"\"" + (args.length > 0 ? " true " : " false ") + (args.length > 0 && subC.equals(args[0]) ? "true" : "false"));
                        if (nArgs.length > 0 && subC.equals(nArgs[0])) {
                            current = subC;
                            i++;
                            nArgs = Arrays.copyOfRange(nArgs, 1, nArgs.length);
                            end = false;
                            if (current.thirdExecutable && (!(sender instanceof Player) || sender.hasPermission("blueUtils.thirdExecution"))) {
                                Player p = Bukkit.getPlayer(args[0]);
                                if (p != null) {
                                    third = true;
                                    sender = p;
                                    args = Arrays.copyOfRange(args, 1, args.length);
                                }
                            }
                            break;
                        }
                    }
                }
                if (sender instanceof Player && !sender.hasPermission(current.permission)) {
                    sender.sendMessage("§cDazu hast du keine Rechte!");
                    return true;
                }
                current.execute(sender, nArgs, sender instanceof Player, third, originalSender);
                break;
            }
        }
        return true;
    }

    public Command getCommand(String label) {
        for (Command c : commands) {
            if (c.equals(label)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        Command c = getCommand(command.getName());
        //System.out.println("A " + Arrays.toString(args));
        List<String> tab = new ArrayList<>();
        if (c != null) {
            tab.addAll(c.getTabComplete(Arrays.copyOfRange(args, 0, args.length), sender));
        }
        return tab;
    }
}
