/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories;

import core.menu.MenuEntry;

/**
 *
 * @author Shay
 */
public class MenuCategory extends MenuEntry {
    private int parentId;
    
    public MenuCategory() {
        super.isCategory = true;
    };
    
    public MenuCategory(int parentId){
        super.isCategory = true;
        this.parentId = parentId;
    }
    
    public int getParentId(){
        return parentId;
    }
    
    public void setParentId(int parentId){
        this.parentId = parentId;
    }  
}
