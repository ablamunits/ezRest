/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items.dao;

import core.menu.MenuEntry;
import core.menu.items.MenuItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringUtils;

/**
 *
 * @author Shay
 */
public class SqlMenuItemDao implements MenuItemDao{

    @Override
    public MenuEntry getItemById(int itemId) {
       ResultSet menuItemSet = MySqlUtils.getQuery("SELECT * FROM MenuItems WHERE Item_id = " + itemId + ";");
       
       try {
           menuItemSet.first();
           MenuEntry menuItem = buildMenuItem(menuItemSet);
           menuItemSet.close();
           return menuItem;
       } catch (SQLException ex) {
           Logger.getLogger(SqlMenuItemDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }

    @Override
    public void deleteMenuItemById(int itemId) {
        MySqlUtils.updateQuery("DELETE FROM MenuItems WHERE Item_id = " + itemId );
    }

    @Override
    public void createMenuItem(MenuEntry menuItem) {
        String[] columnNames = {
            "Item_id",
            "Price",
            "Cat_id",
            "Title",
        };
                        
        Object[] values = {
            ((MenuItem)menuItem).getItemId(),
            ((MenuItem)menuItem).getPrice(),
            menuItem.getCategoryId(),
            menuItem.getTitle()
        };
        
        String qString = new StringBuilder("INSERT INTO MenuItems ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();
        
        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void updateMenuItem(MenuEntry menuCat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private MenuEntry buildMenuItem(ResultSet menuItemRow) {
        try {
            int itemId = menuItemRow.getInt("Item_id");
            int price = menuItemRow.getInt("Price");
            String title = menuItemRow.getString("Title");
            int categoryId = menuItemRow.getInt("Cat_id");
            
            MenuEntry menuItem = new MenuItem();
            ((MenuItem)menuItem).setItemId(itemId);
            ((MenuItem)menuItem).setPrice(price);
            menuItem.setTitle(title);
            menuItem.setCategoryId(categoryId);

            return menuItem;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuItemDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
