/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class PluginList<E extends AdvancedPlugin> extends HashSet<E> {

    public PluginList(PluginList<E> es) {
        super(es);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     */
    public PluginList() {
    }

    @Nullable
    public E getPlugin(@NotNull String name) {
        for (E e : this) {
            if (e.getName().equalsIgnoreCase(name)) return e;
        }
        return null;
    }

    @Override
    public PluginList<E> clone() {
        return new PluginList<>(this);
    }
}
