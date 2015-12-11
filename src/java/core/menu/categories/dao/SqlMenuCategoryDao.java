/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories.dao;

import core.menu.MenuEntry;
import core.menumanager.SubMenu;
import core.menu.categories.MenuCategory;
import core.menu.items.MenuItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author Shay
 */
public class SqlMenuCategoryDao implements MenuCategoryDao{

    @Override
    public ArrayList<MenuEntry> getCategoryItems(int catId) {
       ResultSet categoryItemsSet = MySqlUtils.getQuery("(SELECT Title, Cat_id as next_category" +
                                                   " FROM MenuCategories Where Parent_id = " + catId + ")" +
                                                   " UNION " +
                                                   " (SELECT Title, NULL as next_category" +
                                                   " FROM MenuItems Where Cat_id = " + catId + ");");
                
           ArrayList<MenuEntry> categoryItems = buildCategoryItems(categoryItemsSet, catId);          
           return categoryItems;
    }

    @Override
    public void deleteMenuCatById(int catId) {
        //Manager wants to delete Menu Category - assuming every menu category has children
        //of another categories or menu items, we will need to delete them also.
        //*assuming we will need to loop by all the children and delete.
    }
    
    @Override
    public void createMenuCat(MenuCategory newMenuCat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<MenuEntry> buildCategoryItems(ResultSet categoryItemsSet, int categoryId) 
    {    
        try {
            MenuEntry menuEntry;
            ArrayList<MenuEntry> categoryItems = new ArrayList<MenuEntry>() {};

            while (categoryItemsSet.next()) {
                String title = categoryItemsSet.getString("Title");
                int nextCategory = categoryItemsSet.getInt("next_category");
                
                if (categoryItemsSet.wasNull()) {
                    // If next_category was null, its a regular menu item.
                    menuEntry = new MenuItem();
                } else {
                    // If next_category isn't null, its a category.
                    menuEntry = new MenuCategory();
                    menuEntry.setNextCategoryId(nextCategory);
                }
                
                menuEntry.setTitle(title);
                menuEntry.setCategoryId(categoryId);
                categoryItems.add(menuEntry);
            }
            
            categoryItemsSet.close();
            return categoryItems;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuCategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
