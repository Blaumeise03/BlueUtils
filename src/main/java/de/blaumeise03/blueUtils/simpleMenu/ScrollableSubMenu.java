/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ScrollableSubMenu extends ScrollableMenu implements MenuChild {
    private List<MenuChild> children;
    private int index;
    private String name = null;
    private ItemStack icon = null;

    public ScrollableSubMenu(List<MenuChild> content, String name, ItemStack icon, int pageSize) {
        super(content, pageSize);
        this.name = name;
        this.icon = icon;
    }

    @Override
    public void onClick(Player p, MenuSession session) {
        session.goTo(this);
    }

    @Override
    public ItemStack render() {
        ItemStack render;
        if (icon == null) render = new ItemStack(Material.ZOMBIE_HEAD);
        else render = icon;
        ItemMeta iMeta = render.getItemMeta();
        assert iMeta != null;
        iMeta.setDisplayName("§a" + name);
        render.setItemMeta(iMeta);
        return render;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }
}
