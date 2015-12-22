/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items.dao;

import config.MySqlConfig;
import core.menu.MenuEntry;
import core.menu.items.MenuItem;
import core.menu.items.MenuItemsOverview;
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
public class SqlMenuItemDao implements MenuItemDao {
    private final String[] columnNames = {
        "Cat_id",
        "Title",
        "Item_id",
        "Price"
    };
            
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
        MySqlUtils.updateQuery("DELETE FROM MenuItems WHERE Item_id = " + itemId);
    }

    @Override
    public void createMenuItem(MenuItem menuItem) {
        Object[] values = getObjectValues(menuItem);

        String qString = new StringBuilder("INSERT INTO MenuItems ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();

        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void updateMenuItem(int itemId, MenuItem menuItem) {
        Object[] values = getObjectValues(menuItem);

        StringBuilder qString = new StringBuilder("UPDATE " + MySqlConfig.Tables.MENU_ITEMS + " SET ");
        qString.append(MySqlUtils.updateSetString(this.columnNames, values))
                .append(" WHERE Item_id = ").append(itemId);

        System.out.println("update query:" + qString.toString());
        MySqlUtils.updateQuery(qString.toString());
    }

    private MenuEntry buildMenuItem(ResultSet menuItemRow) {
        try {
            int itemId = menuItemRow.getInt("Item_id");
            int price = menuItemRow.getInt("Price");
            String title = menuItemRow.getString("Title");
            int categoryId = menuItemRow.getInt("Cat_id");

            MenuEntry menuItem = new MenuItem();
            ((MenuItem) menuItem).setItemId(itemId);
            ((MenuItem) menuItem).setPrice(price);
            menuItem.setTitle(title);
            menuItem.setCategoryId(categoryId);

            return menuItem;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuItemDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<MenuItemsOverview> getMenuItemsOverview() {
        ResultSet itemsOverviewSet = MySqlUtils.getQuery("SELECT M.Title, O.Item_id, count(*) as Number_Of_Tables, sum(Quantity) as Quantity "
                                                         + " FROM " + MySqlConfig.Tables.ORDER_ITEMS + " O, " + MySqlConfig.Tables.MENU_ITEMS + " M "
                                                         + " WHERE M.Item_id = O.Item_id "
                                                         + " Group by Item_id");

        try {
            ArrayList<MenuItemsOverview> itemsOverview = new ArrayList<MenuItemsOverview>() {
            };

            while (itemsOverviewSet.next()) {
                itemsOverview.add(buildItemOverview(itemsOverviewSet));
            }

            return itemsOverview;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuItemDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private MenuItemsOverview buildItemOverview(ResultSet itemsOverviewRow) throws SQLException {
        String title = itemsOverviewRow.getString("Title");
        int itemId = itemsOverviewRow.getInt("Item_id");
        int numOfTables = itemsOverviewRow.getInt("Number_Of_Tables");
        int quantity = itemsOverviewRow.getInt("Quantity");

        MenuItemsOverview itemOverview = new MenuItemsOverview(title, itemId, numOfTables, quantity);

        return itemOverview;
    }

    private Object[] getObjectValues(MenuEntry menuItem) {
        Object[] values = {
            menuItem.getCategoryId() == 0 ? null : menuItem.getCategoryId(),
            menuItem.getTitle(),
            ((MenuItem)menuItem).getItemId() == 0 ? null : ((MenuItem)menuItem).getItemId(),
            ((MenuItem)menuItem).getPrice()
        };

        return values;
    }
}
