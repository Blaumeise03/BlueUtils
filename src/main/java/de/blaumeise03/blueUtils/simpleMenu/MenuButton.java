/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class represents a button inside the menu. If
 * clicked it will execute the {@link #onClick(Player, MenuSession)}-
 * method.
 */
public abstract class MenuButton implements MenuChild {
    private final ItemStack icon;
    private final String name;
    private int index;

    /**
     * Constructor for the menu.
     *
     * @param icon  the icon of the button (including the display-name).
     * @param name  the name of the button.
     * @param index the index of the button (will be overwritten by the
     *              {@link Menu#rearrange()}-method.
     */
    public MenuButton(ItemStack icon, String name, int index) {
        this.icon = icon;
        this.name = name;
        this.index = index;
    }

    /**
     * Constructor for the menu.
     *
     * @param icon the icon of the button (including the display-name).
     * @param name the name of the button.
     */
    public MenuButton(ItemStack icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    /**
     * Returns the <code>ItemStack</code> wich will be displayed.
     *
     * @return the <code>ItemStack</code>-icon.
     */
    @Override
    public ItemStack render() {
        return icon;
    }

    /**
     * Getter for the index.
     *
     * @return the index of the button.
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * Setter for the index.
     *
     * @param index the new index.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter for the name.
     *
     * @return the name of the button.
     */
    @Override
    public String getName() {
        return name;
    }
}
