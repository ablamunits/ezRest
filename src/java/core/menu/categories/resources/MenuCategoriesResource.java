/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories.resources;

import core.menumanager.SubMenu;
import core.menu.categories.MenuCategories;
import core.menu.categories.dao.SqlMenuCategoriesDao;
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
    private final SqlMenuCategoriesDao menuDao;
    
    public MenuCategoriesResource() {
        menuDao = new SqlMenuCategoriesDao();
    }
    
    // Get sub menu from db via GET request
    @GET
    @Path("/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubMenu> getSubMenuCategory(@PathParam("catId") int catId) {
        List<SubMenu> menuCategory = menuDao.getSubMenuCategory(catId);
        return menuCategory;
    }

    // Add a new menu category to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewMenuCat(@QueryParam("catId") int catId,
                               @QueryParam("title") String title,
                               @QueryParam("parentId") int parentId)
    {
        MenuCategories newMenuCat = new MenuCategories(catId, title, parentId);

        menuDao.createMenuCat(newMenuCat);
    }
    
    @POST
    @Path("/delete/{catId}")
    public void deleteMenuCatById(@PathParam("catId") int catId) {
        menuDao.deleteMenuCatById(catId);
    }
    
}
