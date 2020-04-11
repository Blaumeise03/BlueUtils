/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.command;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import javax.annotation.Nullable;
import java.util.*;

public abstract class Command {
    String label;
    boolean onlyPlayer = true;
    boolean thirdExecutable = false;
    Permission permission = null;
    Set<Command> subCommands = new HashSet<>();

    public Command(String label) {
        this.label = label;
    }

    public Command(String label, boolean onlyPlayer, boolean thirdExecutable) {
        this.label = label;
        this.onlyPlayer = onlyPlayer;
        this.thirdExecutable = thirdExecutable;
    }

    public Command(String label, boolean onlyPlayer, boolean thirdExecutable, Permission permission) {
        this.label = label;
        this.onlyPlayer = onlyPlayer;
        this.thirdExecutable = thirdExecutable;
        this.permission = permission;
    }

    /**
     * This method is called when a player executes this command
     *
     * @param sender         the {@link CommandSender} who executes the command
     * @param args           the arguments passed to the command
     * @param isPlayer       if the <code>sender</code> is a {@link org.bukkit.entity.Player Player}
     * @param isThird        if the command was executed by the <code>originalSender</code> for another player
     *                       e.g: /command player args.. --> the command, if {@link Command#thirdExecutable} is true,
     *                       gets executed at the 'player' passed as first argument
     * @param originalSender The original sender, equals <code>sender</code> if command was not third-executed
     */
    public abstract void execute(CommandSender sender, String[] args, boolean isPlayer, boolean isThird, CommandSender originalSender);

    /**
     * Method for additional tab-arguments.
     *
     * @param args the arguments passed to the command (may contain sub-commands)
     * @return a list with additional arguments for the command
     * @implNote Overwrite this method and let it return all arguments which should be suggested e.g. player names
     */
    public @Nullable
    List<String> getAdditionalTabArguments(@Nullable String[] args) {
        return null;
    }

    public void addParameter(Command subCommand) {
        subCommands.add(subCommand);
    }

    public boolean equals(String s) {
        return label.equalsIgnoreCase(s);
    }

    public String getLabel() {
        return label;
    }

    public List<String> getTabComplete(String[] args) {
        List<String> tab = new ArrayList<>();
        //System.out.println("B " + Arrays.toString(args));
        if (args.length > 0) {
            String arg = args[0].toLowerCase();
            for (Command c : subCommands) {
                if (c.label.toLowerCase().startsWith(arg)) {
                    if (args.length > 1) {
                        tab.addAll(c.getTabComplete(Arrays.copyOfRange(args, 1, args.length)));
                    } else {
                        tab.add(c.getLabel());
                    }
                }
            }
            List<String> l = getAdditionalTabArguments(args);
            if (l != null) tab.addAll(l);
        } else {
            for (Command c : subCommands) {
                tab.add(c.label);
            }
            List<String> l = getAdditionalTabArguments(null);
            if (l != null) tab.addAll(l);
        }
        return tab;
    }
}
