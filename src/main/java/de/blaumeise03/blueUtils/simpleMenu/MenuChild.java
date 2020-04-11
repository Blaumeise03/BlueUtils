/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This interface represents all contents of any {@link Menu menu}.
 */
public interface MenuChild {

    /**
     * This method will be called if the player clicks on
     * the item. It defines what should happen. (e.g.
     * a new {@link SubMenu SubMenu} will be opened.
     *
     * @param p       the player who clicked.
     * @param session the {@link MenuSession} of the player.
     */
    void onClick(Player p, MenuSession session);

    /**
     * Getter for the <code>ItemStack</code>-icon
     *
     * @return the icon.
     */
    ItemStack render();

    /**
     * Getter for the index.
     *
     * @return the index.
     */
    int getIndex();

    /**
     * Setter for the index.
     *
     * @param index the new index.
     */
    void setIndex(int index);

    /**
     * Getter for the name.
     *
     * @return the name.
     */
    String getName();
}
