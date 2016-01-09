/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items.resources;

import core.menu.MenuEntry;
import core.menu.items.MenuItem;
import core.menu.items.MenuItemsOverview;
import core.menu.items.dao.SqlMenuItemDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Shay
 */
// Resource for MySQL table - all menu items
@Path("/menu/item")
public class MenuItemsResource {
    private final SqlMenuItemDao menuItemDao;
    
    public MenuItemsResource() {
        menuItemDao = new SqlMenuItemDao();
    }
    
    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuItemsOverview> getMenuItemsOverview(){
        return menuItemDao.getMenuItemsOverview();
    }
    
    // Get a menu items by id from db via GET request
    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public MenuItem getMenuItem(@PathParam("itemId") int itemId) {
        MenuItem menuItem = menuItemDao.getItemById(itemId);
        return menuItem;
    }
    
    // Add a new menu item to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMenuItem(MenuItem menuItem)
    {
        menuItemDao.createMenuItem(menuItem);
    }
    
    @POST
    @Path("/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMenuItem(@PathParam("itemId") int itemId, MenuItem menuItem) {
        menuItemDao.updateMenuItem(itemId, menuItem);
    }
    
    @POST
    @Path("/delete/{itemId}")
    public void deleteMenuItemById(@PathParam("itemId") int itemId) {
        menuItemDao.deleteMenuItemById(itemId);
    }
}
