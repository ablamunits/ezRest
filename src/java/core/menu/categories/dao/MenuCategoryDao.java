/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories.dao;

import core.menu.MenuEntry;
import core.menu.categories.MenuCategory;
import java.util.List;

/**
 *
 * @author Shay
 */
// CRUD -> Create, Retrieve, Update, Delete
public interface MenuCategoryDao {
    List<MenuEntry> getCategoryItems(int catId);
    void deleteMenuCatById(int catId);
    void createMenuCat(MenuCategory newMenuCat);
    //void updateMenuCat(SubMenu menuCat);
}
