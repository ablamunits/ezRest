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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createMenuItem(MenuEntry menuItem) {
        String qString = "INSERT INTO MenuItems "
                + "(Cat_id, Title, Price, Item_id) "
                + "VALUES " + MySqlUtils.valueString(menuItem.getCategoryId(),
                                                     menuItem.getTitle(),
                                                     ((MenuItem) menuItem).getPrice(),
                                                     ((MenuItem) menuItem).getItemId());

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
            
            MenuEntry menuItem = new MenuItem(itemId, price);
            menuItem.setTitle(title);
            menuItem.setCategoryId(categoryId);

            return menuItem;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuItemDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
