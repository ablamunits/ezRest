/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menumanager.menucategories.dao;

import core.menumanager.SubMenu;
import core.menumanager.menucategories.MenuCategories;
import java.util.List;

/**
 *
 * @author Shay
 */
// CRUD -> Create, Retrieve, Update, Delete
public interface MenuCategoriesDao {
    List<SubMenu> getSubMenuCategory(int catId);
    void deleteMenuCatById(int catId);
    void createMenuCat(MenuCategories newMenuCat);
    //void updateMenuCat(SubMenu menuCat);
}
