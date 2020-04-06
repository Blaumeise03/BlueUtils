/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.spigotUtils;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin main-class of this library to get loaded by the server. Should not be used for anything or this library can get broken.
 *
 * @author Blaumeise03
 * @version 2.0
 * @implSpec DO NOT USE THIS CLASS!! This class is only for internal reasons and could cause errors if used.
 * @since 1.8
 */
public class Plugin extends JavaPlugin {
    private static JavaPlugin plugin = null;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        getLogger().info("Spigot Utils by Blaumeise03. For Updates look at http://blaumeise03.de");
        getLogger().info("Information: Some older plugins could cause errors if they are for an older/newer version of this library so keep up-to-date.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
