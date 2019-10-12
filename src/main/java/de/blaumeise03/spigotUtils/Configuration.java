/*
 *     Copyright (C) 2019  Blaumeise03 - bluegame61@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.blaumeise03.spigotUtils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import de.blaumeise03.spigotUtils.exceptions.ConfigurationNotFoundException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * For easy and fast configurations.
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
    public Configuration(@Nullable String name, @NotNull JavaPlugin plugin) {
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
    private void setup(boolean createFile) throws ConfigurationNotFoundException {
        if (!file.exists() && createFile) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ConfigurationNotFoundException("Can't create file!", file);
            }
            plugin.saveResource(name, false);
            plugin.saveResource(file.getName(), false);
        } else throw new ConfigurationNotFoundException(file);
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
