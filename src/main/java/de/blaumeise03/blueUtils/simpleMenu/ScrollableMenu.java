/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import de.blaumeise03.blueUtils.Head;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * This class represents a multi-page-menu.
 */
public class ScrollableMenu extends Menu implements Scrollable {
    private final int pageSize;
    private final MenuButton left;
    private final MenuButton right;


    public ScrollableMenu(List<MenuChild> content, int pageSize) {
        super(content);
        this.pageSize = pageSize;

        ItemStack leftStack = Head.getSkull("8652e2b936ca8026bd28651d7c9f2819d2e923697734d18dfdb13550f8fdad5f");
        ItemMeta iMetaL = leftStack.getItemMeta();
        assert iMetaL != null;
        iMetaL.setDisplayName("§6Vorherige Seite");
        leftStack.setItemMeta(iMetaL);
        left = new MenuButton(leftStack, "Zurück") {
            @Override
            public void onClick(Player p, MenuSession session) {
                goPrevious(session);
            }
        };
        ItemStack rightStack = Head.getSkull("2a3b8f681daad8bf436cae8da3fe8131f62a162ab81af639c3e0644aa6abac2f");
        ItemMeta iMetaR = rightStack.getItemMeta();
        assert iMetaR != null;
        iMetaR.setDisplayName("§6Nächste Seite");
        rightStack.setItemMeta(iMetaR);
        right = new MenuButton(rightStack, "Weiter") {
            @Override
            public void onClick(Player p, MenuSession session) {
                goNext(session);
            }
        };
    }

    void renderMenu(Inventory inventory, MenuSession session) {
        inventory.clear();
        session.viewContent.clear();
        int startIndex = session.startIndex;
        for (MenuChild child : content) {
            if (child.getIndex() >= startIndex) {
                if (child.getIndex() - startIndex < pageSize) {
                    session.viewContent.put(child.getIndex() - startIndex, child);
                    inventory.setItem(child.getIndex() - startIndex, child.render());
                }
            }
        }
        session.viewContent.put(inventory.getSize() - 6, left);
        inventory.setItem(inventory.getSize() - 6, left.render());
        session.viewContent.put(inventory.getSize() - 4, right);
        inventory.setItem(inventory.getSize() - 4, right.render());
    }

    @Override
    public void processClick(Player p, int slot, MenuSession session) {
        if (session.viewContent.containsKey(slot))
            session.viewContent.get(slot).onClick(p, session);
    }

    @Override
    public void goPrevious(MenuSession session) {
        if (session.startIndex != 0) {
            session.startIndex -= pageSize - 1;
            session.update();
        }
    }

    @Override
    public void goNext(MenuSession session) {
        if (session.startIndex + pageSize < maxIndex) {
            session.startIndex += pageSize - 1;
            session.update();
        }
    }

}
