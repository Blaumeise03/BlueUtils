/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HotBarSession implements Session {
    static Map<Player, HotBarSession> sessions = new HashMap<>();
    Player player;
    HotBarMenu menu;
    MenuSession session = null;
    boolean active = true;

    public HotBarSession(HotBarMenu current, Player viewer) {
        this.menu = current;
        this.player = viewer;
        sessions.put(player, this);
    }

    public void refresh() {
        menu.renderMenu(player.getInventory());
        if (session != null) {
            session.viewer.closeInventory();
            session = null;
        }
        active = true;
        player.updateInventory();
    }

    @Override
    public void executeClick(Player p, int slot) {
        for (MenuChild child : menu.content) {
            if (child.getIndex() == slot) {
                child.onClick(player, null);
                break;
            }
        }
    }

    @Override
    public void goTo(Menu menu) {
        active = false;
        session = new MenuSession(menu, player, 45 - 9, "");
    }

    public void close() {
        sessions.remove(player);
        for (int i = 0; i < 9; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isActive() {
        return active;
    }
}
