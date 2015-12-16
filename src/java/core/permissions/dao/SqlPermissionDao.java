/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.permissions.dao;

import core.permissions.Permission;
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
 * @author borisa
 */
public class SqlPermissionDao implements PermissionDao {
    
    @Override
    public List<Permission> getAllPermissions() {
       ResultSet permissionSet = MySqlUtils.getQuery("SELECT * FROM permissions;");
       try {
           ArrayList<Permission> permissions = new ArrayList<Permission>() {};
           
           while(permissionSet.next()) {
               permissions.add(buildPermission(permissionSet));
           }
           
           return permissions;
       } catch (SQLException ex) {
           Logger.getLogger(SqlPermissionDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }        
    }

    @Override
    public Permission findPermissionById(int id) {
        ResultSet permissionSet = MySqlUtils.getQuery("SELECT * FROM permissions WHERE Permission_id = " + id);
        try {      
            permissionSet.first();
            Permission permission = buildPermission(permissionSet);
            return permission;
        } catch (SQLException ex) {
            Logger.getLogger(SqlPermissionDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void createPermission(Permission permission) {
        // TODO: Move to a different class that contains the Permission table column names..
        // TODO: Do the same with other tables...
        String[] columnNames = {
            "Permission_id",
            "Title",
            "ADD_PRODUCT",
            "ADD_EMPLOYEE",
            "CANCEL_ORDER",
            "ADD_DISCOUNT",
            "EDIT_MENU"
        };
                
        Object[] values = {
            permission.getPermissionId(),
            permission.getTitle(),
            permission.getAuthorizedActions().contains(Permission.AuthorizedActions.ADD_PRODUCT),
            permission.getAuthorizedActions().contains(Permission.AuthorizedActions.ADD_EMPLOYEE),
            permission.getAuthorizedActions().contains(Permission.AuthorizedActions.CANCEL_ORDER),
            permission.getAuthorizedActions().contains(Permission.AuthorizedActions.ADD_DISCOUNT),
            permission.getAuthorizedActions().contains(Permission.AuthorizedActions.EDIT_MENU)                
        };
        
        String qString = new StringBuilder("INSERT INTO permissions ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();
        
        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void deletePermissionById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePermission(Permission permission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Permission buildPermission(ResultSet permissionRow) throws SQLException {
        int id = permissionRow.getInt("Permission_id");
        String title = permissionRow.getString("Title");
        ArrayList<Permission.AuthorizedActions> actions = new ArrayList<>();
        
        for (Permission.AuthorizedActions action : Permission.AuthorizedActions.values()) {
            if (permissionRow.getBoolean(action.toString())) {
                actions.add(action);
            }
        }
        
        Permission permission = new Permission();
        permission.setPermissionId(id);
        permission.setTitle(title);
        permission.setAuthorizedActions(actions);
        
        return permission;
    }
}
