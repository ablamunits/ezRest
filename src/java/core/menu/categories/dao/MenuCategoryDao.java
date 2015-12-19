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
    List<MenuCategory> getAllCategories();
    List<MenuEntry> getCategoryById(int catId);
    void deleteMenuCategoryById(int catId);
    void createMenuCategory(MenuCategory newMenuCat);
    void updateMenuCategory(int id, MenuCategory menuCat);
}
