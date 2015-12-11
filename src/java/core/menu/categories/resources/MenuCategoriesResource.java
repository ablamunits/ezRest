/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories.resources;

import core.menu.MenuEntry;
import core.menu.categories.MenuCategory;
import core.menu.categories.dao.SqlMenuCategoryDao;
import java.util.List;
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
// Resource for MySQL table - all menu categories
@Path("/menu")
public class MenuCategoriesResource {    
    private final SqlMenuCategoryDao menuDao;
    
    public MenuCategoriesResource() {
        menuDao = new SqlMenuCategoryDao();
    }
    
    // Get a category items by id from db via GET request
    @GET
    @Path("/category-{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuEntry> getMenuCategory(@PathParam("catId") int catId) {
        List<MenuEntry> menuCategory = menuDao.getCategoryItems(catId);
        return menuCategory;
    }

    // Add a new menu category to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMenuCat(@QueryParam("catId") int catId,
                               @QueryParam("title") String title,
                               @QueryParam("parentId") int parentId)
    {
        MenuCategory newMenuCategory = new MenuCategory();
        newMenuCategory.setCategoryId(catId);
        newMenuCategory.setTitle(title);
        newMenuCategory.setParentId(parentId);

        menuDao.createMenuCat(newMenuCategory);
    }
    
    @POST
    @Path("/delete/{catId}")
    public void deleteMenuCatById(@PathParam("catId") int catId) {
        menuDao.deleteMenuCatById(catId);
    }
    
}
