/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class HotBarMenu extends Menu {

    /**
     * Constructor for the menu.
     *
     * @param content all content-objects wich are included in the menu.
     */
    public HotBarMenu(List<MenuChild> content) {
        super(content);
        maxIndex = 8;
    }

    /**
     * UNUSED METHOD
     *
     * @deprecated is not necessary. Just set the index of all {@link MenuChild#setIndex(int)}  children}
     */
    @Override
    @Deprecated
    public void rearrange() {

    }

    /**
     * Method for handling a click inside the menu (inventory).
     *
     * @param p       the player who clicked.
     * @param slot    the clicked slot.
     * @param session the {@link MenuSession} of the player.
     */
    @Override
    public void processClick(Player p, int slot, MenuSession session) {
        super.processClick(p, slot, session);
    }

    public void addChild(MenuChild child) {
        content.add(child);
        for (HotBarSession session : HotBarSession.sessions.values()) {
            if (session.active)
                session.refresh();
        }
    }

    /**
     * Method for rendering the whole menu. It will override
     * all slots of the inventory.
     *
     * @param inventory the inventory of the menu.
     */
    void renderMenu(PlayerInventory inventory) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
        for (MenuChild child : content) {
            if (child.getIndex() < 9)
                inventory.setItem(child.getIndex(), child.render());
        }
    }

    /**
     * Method for rendering the whole menu. It will override
     * all slots of the inventory.
     *
     * @param inventory the inventory of the menu. Must be a {@link PlayerInventory} else a {@link ClassCastException} will be thrown.
     * @throws ClassCastException if the inventory is not a {@link PlayerInventory}.
     */
    @Override
    void renderMenu(Inventory inventory) {
        renderMenu((PlayerInventory) inventory);
    }
}
