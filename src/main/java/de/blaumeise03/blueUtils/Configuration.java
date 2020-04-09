/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import de.blaumeise03.blueUtils.exceptions.ConfigurationNotFoundException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * For easy and fast configurations.
 *
 * @author Blaumeise03
 * @version 1.3
 * @since 1.8
 */
public class Configuration extends YamlConfiguration {
    //private FileConfiguration fileConfiguration;
    private File file;
    private JavaPlugin plugin;
    private String name;

    @Deprecated
    public Configuration(File file, JavaPlugin plugin) {
        this.file = file;
        this.plugin = plugin;
    }

    /**
     * Constructs a new Configuration.
     *
     * @param name   the name of the config (e.g. 'config.yml')
     * @param plugin the <code>JavaPlugin</code>.
     */
    public Configuration(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        if (name == null) name = "config.yml";
        this.name = name;
        file = new File(plugin.getDataFolder(), name);
    }

    /**
     * Setup method.
     *
     * @param createFile If true it will creates a new file if it does not exist.
     * @throws ConfigurationNotFoundException if the configuration file does not
     *                                        exist and if it can't (e.g. if <code>createFile</code> is false) create a
     *                                        new file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void setup(boolean createFile) throws ConfigurationNotFoundException {
        if (!file.exists() && createFile) {
            file.getParentFile().mkdirs();
            /*try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ConfigurationNotFoundException("Can't create file!", file);
            }*/
            plugin.saveResource(name, false);
            //plugin.saveResource(file.getName(), false);
        } else if (!file.exists()) throw new ConfigurationNotFoundException(file);
        //fileConfiguration = new YamlConfiguration();
        try {
            //fileConfiguration.load(file);
            this.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads the config. THIS WILL CLEAR THE CACHE! IF YOU WANT TO KEEP THE CACHE SAVE THE CONFIG BEFORE!
     */
    public void reload() {
        try {
            this.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the config.
     */
    public void save() {
        try {
            this.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
