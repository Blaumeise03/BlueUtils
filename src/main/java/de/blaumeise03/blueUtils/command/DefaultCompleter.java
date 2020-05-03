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
     * Default tab completer for all Players currently online.
     *
     * @param arg The partial argument entered by the sender if available
     * @return All possible arguments
     */
    public static @NotNull List<String> getPlayerComplete(@Nullable String arg) {
        return Bukkit.getServer().getOnlinePlayers()
            .stream()
            .filter((player) -> player.getName().startsWith(arg))
            .collect(Collectors.toList());
    }
}
