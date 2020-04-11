/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import de.blaumeise03.blueUtils.command.Command;
import de.blaumeise03.blueUtils.command.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
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
    static PluginList<AdvancedPlugin> plugins = new PluginList<>();

    private PluginManager pluginManager;
    private CommandHandler handler;
    private final Set<Command> commands = new HashSet<>();

    public static PluginList<AdvancedPlugin> getPlugins() {
        return plugins.clone();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        pluginManager = Bukkit.getPluginManager();
        //de.blaumeise03.spigotUtils.Command.setup(this);
        handler = new CommandHandler();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * This method can be executed with the command /blueUtils reload [PluginName].
     * You can define what should happen, e.g. reloading configs or settings
     *
     * @return true if successful
     */
    public boolean onReload() {
        return false;
    }

    /**
     * Use to register new events.
     *
     * @param listener the event-class.
     */
    protected void registerEvent(Listener listener) {
        pluginManager.registerEvents(listener, this);
    }

    /*
     * Executes the commands automatically.
     *
     * @param sender  the CommandSender.
     * @param command the org.bukkit.Command.
     * @param label   the label of the command.
     * @param args    the arguments of the executed command.
     * @return always true.
     *
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return handler.executeCommand(command, args, sender, label);
    }*/

    public void warn(String message) {
        getLogger().log(Level.WARNING, message);
    }

    public void info(String message) {
        getLogger().log(Level.INFO, message);
    }

    public CommandHandler getHandler() {
        return handler;
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public Set<Command> getCommands() {
        return commands;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        plugins.add(this);
    }
}
