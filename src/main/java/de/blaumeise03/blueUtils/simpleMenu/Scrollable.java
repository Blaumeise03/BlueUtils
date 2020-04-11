/*
 * Copyright (c) 2020 Blaumeise03
 */

package de.blaumeise03.blueUtils.simpleMenu;

/**
 * This interface represents a scrollable-menu. It gives the ability
 * of scrolling through multiple pages of {@link MenuChild MenuChilds}.
 */
public interface Scrollable {

    /**
     * Method for opening the previous page.
     *
     * @param session the {@link MenuSession} of the player.
     */
    void goPrevious(MenuSession session);

    /**
     * Method for opening the next page.
     *
     * @param session the {@link MenuSession} of the player.
     */
    void goNext(MenuSession session);
}
