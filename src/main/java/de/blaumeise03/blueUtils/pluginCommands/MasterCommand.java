/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.pluginCommands;

import de.blaumeise03.blueUtils.AdvancedPlugin;
import de.blaumeise03.blueUtils.Plugin;
import de.blaumeise03.blueUtils.command.Command;
import de.blaumeise03.blueUtils.exceptions.CommandNotFoundException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MasterCommand {
    private MasterCommand() {
    }

    public static void init() {
        Command command = new Command("blueUtils", false, false) {

            /**
             * This method is called when a player executes this command
             *
             * @param sender         the {@link CommandSender} who executes the command
             * @param args           the arguments passed to the command
             * @param isPlayer       if the <code>sender</code> is a {@link Player Player}
             * @param isThird        if the command was executed by the <code>originalSender</code> for another player
             *                       e.g: /command player args.. --> the command, if thirdExecutable is true,
             *                       gets executed at the 'player' passed as first argument
             * @param originalSender The original sender, equals <code>sender</code> if command was not third-executed
             */
            @Override
            public void execute(CommandSender sender, String[] args, boolean isPlayer, boolean isThird, CommandSender originalSender) {
                sender.sendMessage("§cUse /blueUtils reload <Plugin>");
            }
        };
        Command reloadCmd = new Command("reload", false, false, new Permission("blueUtils.command.reloadPlugin")) {

            /**
             * This method is called when a player executes this command
             *
             * @param sender         the {@link CommandSender} who executes the command
             * @param args           the arguments passed to the command
             * @param isPlayer       if the <code>sender</code> is a {@link Player Player}
             * @param isThird        if the command was executed by the <code>originalSender</code> for another player
             *                       e.g: /command player args.. --> the command, if thirdExecutable is true,
             *                       gets executed at the 'player' passed as first argument
             * @param originalSender The original sender, equals <code>sender</code> if command was not third-executed
             */
            @Override
            public void execute(CommandSender sender, String[] args, boolean isPlayer, boolean isThird, CommandSender originalSender) {
                if (args.length > 1) {
                    for (AdvancedPlugin plugin : AdvancedPlugin.getPlugins()) {
                        if (plugin.getName().equalsIgnoreCase(args[0])) {
                            plugin.onReload();
                            sender.sendMessage("§aPlugin reloaded!");
                            return;
                        }
                    }
                } else {
                    sender.sendMessage("§cPlease add a Plugin: /blueUtils reload <Plugin>");
                }
            }

            /**
             * Method for additional tab-arguments.
             *
             * @param args the arguments passed to the command (may contain sub-commands)
             * @return a list with additional arguments for the command
             * @implNote Overwrite this method and let it return all arguments which should be suggested e.g. player names
             */
            @Nullable
            @Override
            public List<String> getAdditionalTabArguments(@Nullable String[] args) {
                List<String> pluginsS = new ArrayList<>();
                for (AdvancedPlugin plugin : AdvancedPlugin.getPlugins()) {
                    String n = plugin.getName();
                    if (args != null && args.length == 1) {
                        if (n.toLowerCase().startsWith(args[0].toLowerCase()))
                            pluginsS.add(n);
                    } else if (args == null || args.length == 0) {
                        pluginsS.add(plugin.getName());
                    }
                }

                return pluginsS;
            }
        };
        command.addParameter(reloadCmd);
        try {
            Plugin.getPlugin().getHandler().addCommand(command);
        } catch (CommandNotFoundException e) {
            e.printStackTrace();
        }
    }
}
