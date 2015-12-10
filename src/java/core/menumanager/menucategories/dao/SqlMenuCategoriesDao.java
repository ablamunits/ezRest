/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menumanager.menucategories.dao;

import core.menumanager.SubMenu;
import core.menumanager.menucategories.MenuCategories;
import static java.sql.JDBCType.NULL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author Shay
 */
public class SqlMenuCategoriesDao implements MenuCategoriesDao{

    @Override
    public ArrayList<SubMenu> getSubMenuCategory(int catId) {
       ResultSet subMenuSet = MySqlUtils.getQuery("(SELECT Title, Cat_id as next_category" +
                                                   " FROM MenuCategories Where Parent_id = " + catId + ")" +
                                                   " UNION " +
                                                   " (SELECT Title, NULL as next_category" +
                                                   " FROM MenuItems Where Cat_id = " + catId + ");");
                
           ArrayList<SubMenu> subMenu = buildSubMenuSet(subMenuSet);          
           return subMenu;
    }

    @Override
    public void deleteMenuCatById(int catId) {
        //Manager wants to delete Menu Category - assuming every menu category has children
        //of another categories or menu items, we will need to delete them also.
        //*assuming we will need to loop by all the children and delete.
    }
    
    @Override
    public void createMenuCat(MenuCategories newMenuCat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<SubMenu> buildSubMenuSet(ResultSet subMenuSet) 
    {    
        try {
            ArrayList<SubMenu> subMenu = new ArrayList<SubMenu>() {};

            while (subMenuSet.next()) {
                String title = subMenuSet.getString("Title");
                int nextCategory = subMenuSet.getInt("next_category");

                if (subMenuSet.wasNull()) {
                    subMenu.add(new SubMenu(title, SubMenu.NULL));
                } else {
                    subMenu.add(new SubMenu(title, nextCategory));
                }
            }
            
            subMenuSet.close();
            return subMenu;
        } catch (SQLException ex) {
            Logger.getLogger(SqlMenuCategoriesDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
