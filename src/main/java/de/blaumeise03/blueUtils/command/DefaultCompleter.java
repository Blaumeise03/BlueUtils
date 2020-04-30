/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DefaultCompleter {
    private DefaultCompleter() {
    }


    /**
     * Default tabCompleter for all online Players
     *
     * @param arg the partial argument entered by the sender if available
     * @return all possible arguments
     */
    public static @NotNull List<String> getPlayerComplete(@Nullable String arg) {
        List<String> result = new ArrayList<>();
        if (arg == null || arg.equals("")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                result.add(p.getName());
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(arg.toLowerCase())) result.add(p.getName());
            }
        }
        return result;
    }
}
