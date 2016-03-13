/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories.dao;

import config.MySqlConfig;
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
public class SqlMenuCategoryDao implements MenuCategoryDao {

    private final String[] columnNames = {
        "Cat_id",
        "Title",
        "Parent_id",};

    @Override
    public List<MenuCategory> getAllCategories() {
        ResultSet categoriesSet = MySqlUtils.getQuery("SELECT * FROM " + MySqlConfig.Tables.MENU_CATEGORIES);

        try {
            ArrayList<MenuCategory> categories = new ArrayList<MenuCategory>() {
            };

            while (categoriesSet.next()) {
                categories.add(buildCategory(categoriesSet));
            }

            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuCategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                categoriesSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqlMenuCategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<MenuEntry> getCategoryById(int catId) {
        ResultSet categoryItemsSet = MySqlUtils.getQuery("(SELECT Title, Cat_id as next_category, Null as Item_id "
                + " FROM MenuCategories Where Parent_id = " + catId + ")"
                + " UNION "
                + " (SELECT Title, NULL as next_category, Item_id"
                + " FROM MenuItems Where Cat_id = " + catId + ");");

        ArrayList<MenuEntry> categoryItems = buildCategoryItems(categoryItemsSet, catId);
        
        try {
            categoryItemsSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuCategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return categoryItems;
    }

    @Override
    public void deleteMenuCategoryById(int catId) {
        MySqlUtils.updateQuery("DELETE FROM MenuCategories WHERE Cat_id = " + catId);
    }

    @Override
    public void updateMenuCategory(int catId, MenuCategory menuCategory) {
        Object[] values = getObjectValues(menuCategory);

        StringBuilder qString = new StringBuilder("UPDATE " + MySqlConfig.Tables.MENU_CATEGORIES + " SET ");
        qString.append(MySqlUtils.updateSetString(this.columnNames, values))
                .append(" WHERE Cat_id=").append(catId);

        MySqlUtils.updateQuery(qString.toString());
    }

    @Override
    public void createMenuCategory(MenuCategory menuCategory) {
        Object[] values = getObjectValues((MenuCategory) menuCategory);

        String qString = new StringBuilder("INSERT INTO MenuCategories ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();

        MySqlUtils.updateQuery(qString);
    }

    private ArrayList<MenuEntry> buildCategoryItems(ResultSet categoryItemsSet, int categoryId) {
        try {
            MenuEntry menuEntry;
            ArrayList<MenuEntry> categoryItems = new ArrayList<MenuEntry>() {
            };

            while (categoryItemsSet.next()) {
                String title = categoryItemsSet.getString("Title");
                int itemId = categoryItemsSet.getInt("Item_id");
                int nextCategory = categoryItemsSet.getInt("next_category");

                if (categoryItemsSet.wasNull()) {
                    // If next_category was null, its a regular menu item.
                    menuEntry = new MenuItem(itemId);
//                    menuEntry.setItemId(itemId);
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

    private Object[] getObjectValues(MenuCategory menuCategory) {
        Object[] values = {
            menuCategory.getCategoryId() == 0 ? null : menuCategory.getCategoryId(),
            menuCategory.getTitle(),
            menuCategory.getParentId() == 0 ? null : menuCategory.getParentId()
        };

        return values;
    }

    private MenuCategory buildCategory(ResultSet categorySet) throws SQLException {

        int categoryId = categorySet.getInt("Cat_Id");
        String title = categorySet.getString("Title");
        int parentId = categorySet.getInt("Parent_Id");

        MenuCategory menuCategory = new MenuCategory();

        menuCategory.setCategoryId(categoryId);
        menuCategory.setParentId(parentId);
        menuCategory.setTitle(title);

        return menuCategory;
    }

}
