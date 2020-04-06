/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.spigotUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * An advanced version of the {@code JavaPlugin}
 *
 * @author Blaumeise03
 * @version 2.0
 * @implSpec Your main-plugin class wich normally {@code extends JavaPlugin} should just {@code extends AdvancedPlugin}
 * @since 1.8
 */
public class AdvancedPlugin extends JavaPlugin {
    //private AdvancedPlugin plugin;
    private PluginManager pluginManager;
    private CommandHandler handler;

    @Override
    public void onEnable() {
        super.onEnable();
        pluginManager = Bukkit.getPluginManager();
        //de.blaumeise03.spigotUtils.Command.setup(this);
        handler = new CommandHandler(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Use to register new events.
     *
     * @param listener the event-class.
     */
    protected void registerEvent(Listener listener) {
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
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return handler.executeCommand(command, args, sender, label);
    }

    public void warn(String message) {
        getLogger().log(Level.WARNING, message);
    }

    public void info(String message) {
        getLogger().log(Level.INFO, message);
    }

    public CommandHandler getHandler() {
        return handler;
    }
}
