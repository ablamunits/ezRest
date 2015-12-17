/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories.dao;

import core.menu.MenuEntry;
import core.menu.categories.MenuCategory;
import core.menu.items.MenuItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringUtils;

/**
 *
 * @author Shay
 */
public class SqlMenuCategoryDao implements MenuCategoryDao{

    @Override
    public List<MenuEntry> getMenu() {
        //TODO
        return null;
    }
        
    @Override
    public ArrayList<MenuEntry> getCategoryById(int catId) {
       ResultSet categoryItemsSet = MySqlUtils.getQuery("(SELECT Title, Cat_id as next_category" +
                                                   " FROM MenuCategories Where Parent_id = " + catId + ")" +
                                                   " UNION " +
                                                   " (SELECT Title, NULL as next_category" +
                                                   " FROM MenuItems Where Cat_id = " + catId + ");");
                
           ArrayList<MenuEntry> categoryItems = buildCategoryItems(categoryItemsSet, catId);          
           return categoryItems;
    }
    
    
    @Override
    public void deleteMenuCategoryById(int catId) {
        //TODO:
        //Manager wants to delete Menu Category - assuming every menu category has children
        //of another categories or menu items, we will need to delete them also.
        //*assuming we will need to loop by all the children and delete.
    }
    
    @Override
    public void updateMenuCategory(MenuEntry menuCat){
        //TODO
    }
    
    @Override
    public void createMenuCategory(MenuEntry menuCategory) {
        String[] columnNames = {
            "Cat_id",
            "Title",
            "Parent_id",
        };
                        
        Object[] values = {
            menuCategory.getCategoryId(),
            menuCategory.getTitle(),
            ((MenuCategory)menuCategory).getParentId()
        };
        
        String qString = new StringBuilder("INSERT INTO MenuCategories ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();
        
        MySqlUtils.updateQuery(qString);
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
