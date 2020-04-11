/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.blaumeise03.blueUtils.simpleMenu.MenuButton;
import de.blaumeise03.blueUtils.simpleMenu.MenuSession;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class Head {
    String link;
    String id;
    String name;
    ItemStack head;

    public Head(String id, String name, String link) {
        this.link = link;
        this.id = id;
        this.name = name;
        head = getSkull(link);
        ItemMeta iMeta = head.getItemMeta();
        assert iMeta != null;
        iMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GOLD + name);
        head.setItemMeta(iMeta);
    }

    public static ItemStack getSkull(String value) {
        if (!value.startsWith("http"))
            return getSkullFromWeb("http://textures.minecraft.net/texture/" + value);
        return getSkullFromWeb(value);
    }

    public static ItemStack getSkullFromWeb(String url) {
        //ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url == null) return head;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url: \"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            assert headMeta != null;
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public MenuButton getButton() {
        return new MenuButton(head, name) {
            @Override
            public void onClick(Player p, MenuSession session) {
                p.getInventory().addItem(head);
                p.sendMessage("§aDu hast den Kopf §6" + name + "§a bekommen!");
                p.playSound(p.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, SoundCategory.MASTER, 1, 1);
            }
        };
    }
}
