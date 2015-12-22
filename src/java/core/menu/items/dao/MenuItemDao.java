/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items.dao;

import core.menu.MenuEntry;
import core.menu.items.MenuItem;
import core.menu.items.MenuItemsOverview;
import java.util.List;

/**
 *
 * @author Shay
 */
// CRUD -> Create, Retrieve, Update, Delete
public interface MenuItemDao {
    MenuEntry getItemById(int itemId);
    void deleteMenuItemById(int itemId);
    void createMenuItem(MenuItem newMenuItem);
    void updateMenuItem(int itemId, MenuItem menuItem);
    List<MenuItemsOverview> getMenuItemsOverview();
}
