/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.commands;

import de.blaumeise03.blueUtils.AdvancedPlugin;
import de.blaumeise03.blueUtils.Command;
import de.blaumeise03.blueUtils.Plugin;
import de.blaumeise03.blueUtils.PluginList;
import org.bukkit.command.CommandSender;

public class MasterCommand {
    public static void init() {
        new Command(Plugin.getPlugin().getHandler(), "blueUtils", "BlueUtils Tools", false, false) {
            /**
             * This method will be executed if a player execute the command.
             *
             * @param args             The arguments from the command (if the player has entered some).
             * @param sender           The CommandSender of the command. If 'onlyPlayer' form the constructor is true this will be a player.
             * @param isPlayer         If the sender is a Player.
             * @param isThirdExecution If the command is executed for another player by the {@code realSender}
             * @param realSender       The real sender of the command if it is executed by another player via third-execution
             */
            @Override
            public void onCommand(String[] args, CommandSender sender, boolean isPlayer, boolean isThirdExecution, CommandSender realSender) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        PluginList<AdvancedPlugin> plugins = Plugin.getPlugins();
                        if (args.length > 1) {
                            AdvancedPlugin plugin = plugins.getPlugin(args[1]);
                            if (plugin != null) {
                                boolean s = plugin.onReload();
                                sender.sendMessage((isPlayer ? (s ? "§a" : "c") : "") + "Plugin " + plugin.getName() + " wurde " + (s ? "" : "nicht ") + "neugeladen!" +
                                        (s ? "" : " Dies kann daran liegen, dass das Plugin dies nicht unterstützt oder das etwas schiefgelaufen ist."));
                            }
                        }
                        StringBuilder msg = new StringBuilder((isPlayer ? "§c" : "") + "You must enter a valid plugin name! All available Plugins:");
                        msg.append((isPlayer ? "§a" : ""));
                        for (AdvancedPlugin plugin : plugins) {
                            msg.append("\n - ").append(plugin.getName());
                        }
                        sender.sendMessage(msg.toString());
                        return;
                    }
                }
            }
        };
    }
}
