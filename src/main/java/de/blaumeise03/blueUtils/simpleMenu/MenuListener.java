/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * This class contains all required {@link EventHandler}s. It
 * must be registered inside the <code>onEnable()</code>-method.
 * BlueUtils will register these events automatically in his <code>onEnable()</code>-method
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
        if (HotBarSession.sessions.containsKey(e.getWhoClicked()) && HotBarSession.sessions.get(e.getWhoClicked()).active) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (HotBarSession.sessions.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (HotBarSession.sessions.containsKey(e.getEntity())) {
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onSwitch(PlayerSwapHandItemsEvent e) {
        if (HotBarSession.sessions.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (HotBarSession.sessions.containsKey(e.getPlayer())) {
            e.setCancelled(true);
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                HotBarSession.sessions.get(e.getPlayer()).executeClick(e.getPlayer(), e.getPlayer().getInventory().getHeldItemSlot());
            }
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        MenuSession.openMenus.remove(e.getPlayer());
        if (HotBarSession.sessions.containsKey(e.getPlayer())) {
            HotBarSession.sessions.get(e.getPlayer()).refresh();
        }
    }
}
