/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.permissions;

import java.util.ArrayList;

/**
 *
 * @author borisa
 */
public class Permission {
    // Todo: add more
    public static enum AuthorizedActions {
        ADD_MENUITEM, ADD_PRODUCT, ADD_EMPLOYEE, CANCEL_ORDER, ADD_DISCOUNT
    }
    
    private int permissionId;
    private String title;
    private ArrayList<AuthorizedActions> actions;
        
    public Permission() {};
    
    public int getPermissionId() {
        return this.permissionId;
    }
    
    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public ArrayList<AuthorizedActions> getAuthorizedActions() {
        return this.actions;
    }
    
    public void setAuthorizedActions(ArrayList<AuthorizedActions> actions) {
        this.actions = actions;
    }
}
