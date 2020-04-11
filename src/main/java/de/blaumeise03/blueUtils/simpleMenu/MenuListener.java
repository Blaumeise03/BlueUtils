/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * This class contains all required <code>EventHandler</code>. It
 * must be registered inside the <code>onEnable()</code>-method.
 */
public class MenuListener implements Listener {

    @SuppressWarnings("SuspiciousMethodCalls")
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (MenuSession.openMenus.containsKey(e.getWhoClicked())) {
            if (e.getSlotType() == InventoryType.SlotType.CONTAINER) {
                if (e.getClickedInventory() == MenuSession.openMenus.get(e.getWhoClicked()).getInventory()) {
                    MenuSession.openMenus.get(e.getWhoClicked()).executeClick((Player) e.getWhoClicked(), e.getSlot());
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        //noinspection SuspiciousMethodCalls
        MenuSession.openMenus.remove(e.getPlayer());
    }
}
