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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Shay
 */
// Resource for MySQL table - all menu categories
@Path("/menu/category")
public class MenuCategoriesResource {    
    private final SqlMenuCategoryDao menuCategoryDao;
    
    public MenuCategoriesResource() {
        menuCategoryDao = new SqlMenuCategoryDao();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuCategory> getAllCategories() {
        return menuCategoryDao.getAllCategories();
    }
    
    
    // Get a category items by id from db via GET request
    @GET
    @Path("/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuEntry> getMenuCategory(@PathParam("catId") int categoryId) {
        List<MenuEntry> menuCategory = menuCategoryDao.getCategoryById(categoryId);
        return menuCategory;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMenuCategory(MenuCategory menuCategory) {
        menuCategoryDao.createMenuCategory(menuCategory);
    }
    
    @POST
    @Path("/{catId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMenuCategory(@PathParam("catId") int catId, MenuCategory menuCategory) {
        menuCategoryDao.updateMenuCategory(catId, menuCategory);
    }
    
    @POST
    @Path("/delete/{catId}")
    public void deleteMenuCatById(@PathParam("catId") int categoryId) {
        menuCategoryDao.deleteMenuCategoryById(categoryId);
    }
    
}
