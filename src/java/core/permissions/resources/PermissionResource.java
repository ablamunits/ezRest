/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.permissions.resources;

import core.permissions.Permission;
import core.permissions.dao.SqlPermissionDao;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author borisa
 */
@Path("/permissions")
public class PermissionResource {    
    private final SqlPermissionDao permissionDao;
    
    public PermissionResource() {
        permissionDao = new SqlPermissionDao();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Permission> getAllPermissions() {
        return permissionDao.getAllPermissions();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPermission(Permission permission) {        
        permissionDao.createPermission(permission);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Permission getPermissionById(@PathParam("id") int id) {
        Permission permission = permissionDao.findPermissionById(id);
        return permission;
    }
    
    @POST
    @Path("/delete/{id}")
    public void deletePermissionById(@PathParam("id") int id) {
        permissionDao.deletePermissionById(id);
    }
    

}
