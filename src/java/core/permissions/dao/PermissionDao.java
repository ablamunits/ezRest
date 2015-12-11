/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.permissions.dao;

import core.permissions.Permission;
import java.util.List;

/**
 *
 * @author borisa
 */
public interface PermissionDao {
    List<Permission> getAllPermissions();
    Permission findPermissionById(int id);
    void createPermission(Permission permission);
    void deletePermissionById(int id);
    void updatePermission(Permission permission);
}
