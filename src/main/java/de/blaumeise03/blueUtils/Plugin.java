/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import de.blaumeise03.blueUtils.commands.MasterCommand;

/**
 * Plugin main-class of this library to get loaded by the server. Should not be used for anything or this library can get broken.
 *
 * @author Blaumeise03
 * @version 2.0
 * @implSpec DO NOT USE THIS CLASS!! This class is only for internal reasons and could cause errors if used.
 * @since 1.8
 */
public class Plugin extends AdvancedPlugin {
    static PluginList<AdvancedPlugin> plugins = new PluginList<>();
    private static AdvancedPlugin plugin = null;

    public static AdvancedPlugin getPlugin() {
        return plugin;
    }

    public static PluginList<AdvancedPlugin> getPlugins() {
        return plugins.clone();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        plugins.add(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        getLogger().info("Spigot Utils by Blaumeise03. For Updates look at http://blaumeise03.de");
        getLogger().info("Information: Some older plugins could cause errors if they are for an older/newer version of this library so keep up-to-date.");
        getLogger().info("Adding commands...");
        MasterCommand.init();
        getLogger().info("Complete!");
    }
}
