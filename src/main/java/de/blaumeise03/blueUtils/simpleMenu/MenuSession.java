/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a menu-session of a player.
 */
public class MenuSession implements Session {
    static Map<Player, MenuSession> openMenus = new HashMap<>();
    final Player viewer;
    final Inventory inventory;
    int startIndex;
    Map<Integer, MenuChild> viewContent;
    Menu current;

    public MenuSession(Menu current, Player viewer, int size, String title) {
        this.current = current;
        this.viewer = viewer;
        inventory = Bukkit.createInventory(viewer, size, title);
        openMenus.put(viewer, this);
        viewContent = new HashMap<>();
        goTo(current);
    }

    @Override
    public void executeClick(Player p, int slot) {
        current.processClick(p, slot, this);
    }

    @Override
    public void goTo(Menu menu) {
        current = menu;
        startIndex = 0;
        if (menu instanceof ScrollableMenu)
            ((ScrollableMenu) menu).renderMenu(inventory, this);
        else menu.renderMenu(inventory);
        viewer.openInventory(inventory);
        //viewer.updateInventory();
        openMenus.put(viewer, this);
    }

    void update() {
        if (current instanceof ScrollableMenu)
            ((ScrollableMenu) current).renderMenu(inventory, this);
        else current.renderMenu(inventory);
        viewer.openInventory(inventory);
        openMenus.put(viewer, this);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
