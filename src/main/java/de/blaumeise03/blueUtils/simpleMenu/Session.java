/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

import org.bukkit.entity.Player;

public interface Session {
    void executeClick(Player p, int slot);

    void goTo(Menu menu);
}
