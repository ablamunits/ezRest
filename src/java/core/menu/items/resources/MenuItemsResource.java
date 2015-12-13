/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items.resources;

import core.menu.MenuEntry;
import core.menu.items.MenuItem;
import core.menu.items.dao.SqlMenuItemDao;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    
    // Get a menu items by id from db via GET request
    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public MenuEntry getMenuItem(@PathParam("itemId") int itemId) {
        MenuEntry menuItem = menuItemDao.getItemById(itemId);
        return menuItem;
    }
    
    // Add a new menu item to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMenuCat(@QueryParam("itemId") int itemId,
                              @QueryParam("price") int price,
                              @QueryParam("title") String title,
                              @QueryParam("catId") int categoryId)
    {
        MenuEntry newMenuItem = new MenuItem(itemId, price);
        newMenuItem.setCategoryId(categoryId);
        newMenuItem.setTitle(title);

        menuItemDao.createMenuItem(newMenuItem);
    }
}
