/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.permissions.resources;

import core.permissions.Permission;
import core.permissions.dao.SqlPermissionDao;
import java.util.ArrayList;
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
    public void addPermission(@QueryParam("permissionId") int permissionId,
                              @QueryParam("title") String title,
                              @QueryParam("authorizedActions") List<String> authorizedActions) {
        
        
        System.out.println("actions: " + authorizedActions.toString());
        Permission permission = new Permission();
        permission.setPermissionId(permissionId);
        permission.setTitle(title);
        
        ArrayList<Permission.AuthorizedActions> actions = new ArrayList<>();
        
        for (String actionString : authorizedActions) {
            actions.add(Permission.AuthorizedActions.valueOf(actionString));
        }
        
        permission.setAuthorizedActions(actions);
        permissionDao.createPermission(permission);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Permission getPermissionById(@PathParam("id") int id) {
        Permission permission = permissionDao.findPermissionById(id);
        return permission;
    }
    

}
