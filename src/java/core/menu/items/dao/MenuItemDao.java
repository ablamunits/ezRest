/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items.dao;

import core.menu.MenuEntry;

/**
 *
 * @author Shay
 */
// CRUD -> Create, Retrieve, Update, Delete
public interface MenuItemDao {
    MenuEntry getItemById(int itemId);
    void deleteMenuItemById(int itemId);
    void createMenuItem(MenuEntry newMenuCat);
    void updateMenuItem(MenuEntry menuCat);
}
