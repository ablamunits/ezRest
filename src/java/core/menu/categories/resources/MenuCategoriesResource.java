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
@Path("/menu/category")
public class MenuCategoriesResource {    
    private final SqlMenuCategoryDao menuCategoryDao;
    
    public MenuCategoriesResource() {
        menuCategoryDao = new SqlMenuCategoryDao();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuEntry> getAllMenu() {
        // TODO
        return menuCategoryDao.getMenu();
    }
    
    // Get a category items by id from db via GET request
    @GET
    @Path("/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MenuEntry> getMenuCategory(@PathParam("catId") int categoryId) {
        List<MenuEntry> menuCategory = menuCategoryDao.getCategoryById(categoryId);
        return menuCategory;
    }

    // Add a new menu category to db via a POST request
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void addNewMenuCat(@QueryParam("categoryId") int categoryId,
//                              @QueryParam("title") String title,
//                              @QueryParam("parentId") int parentId)
//    {
//        MenuEntry newMenuCategory = new MenuCategory(parentId);
//        newMenuCategory.setCategoryId(categoryId);
//        newMenuCategory.setTitle(title);
//
//        menuCategoryDao.createMenuCategory(newMenuCategory);
//    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMenuCategory(MenuCategory menuCategory) {
        menuCategoryDao.createMenuCategory(menuCategory);
    }
    
    @POST
    @Path("/{catId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMenuCategory() {
        // TODO
        menuCategoryDao.updateMenuCategory(null);
    }
    
    @POST
    @Path("/delete/{catId}")
    public void deleteMenuCatById(@PathParam("catId") int categoryId) {
        menuCategoryDao.deleteMenuCategoryById(categoryId);
    }
    
}
