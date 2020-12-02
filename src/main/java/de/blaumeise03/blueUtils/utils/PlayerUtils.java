/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class PlayerUtils {
    private PlayerUtils() {
    }

    public static void clearPlayer(Player player) {
        player.setSaturation(10);
        player.setHealth(20);
        player.setLevel(0);
        player.setExp(0);
        player.setFallDistance(0);
        player.setVelocity(new Vector(0, 0, 0));
        player.closeInventory();
        PlayerInventory pInv = player.getInventory();
        pInv.clear();
        pInv.setItemInOffHand(null);
        pInv.setBoots(null);
        pInv.setLeggings(null);
        pInv.setChestplate(null);
        pInv.setHelmet(null);
        pInv.setHeldItemSlot(0);
        InventoryView pView = player.getOpenInventory();
        pView.getBottomInventory().clear();
        pView.getTopInventory().clear();
        player.setItemOnCursor(null);
        player.updateInventory();
        player.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
    }
}
