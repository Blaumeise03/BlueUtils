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

    public static <E extends Enum<?>> @NotNull List<String> getEnumComplete(@NotNull E e, @Nullable String arg) {
        return getEnumComplete(e.getClass(), arg);
    }

    private static <E extends Enum<?>> @NotNull List<String> getEnumComplete(@NotNull Class<E> c, @Nullable String arg) {
        List<String> result = new ArrayList<>();
        if (arg == null || arg.equals("")) {
            for (E e : c.getEnumConstants()) {
                result.add(e.name());
            }
        } else {
            arg = arg.toLowerCase().trim();
            for (E e : c.getEnumConstants()) {
                if (e.name().toLowerCase().startsWith(arg))
                    result.add(e.name());
            }
        }
        return result;
    }
}
