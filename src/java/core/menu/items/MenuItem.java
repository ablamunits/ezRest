/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items;

import core.menu.MenuEntry;

/**
 *
 * @author Shay
 */
public class MenuItem extends MenuEntry {
    private int itemId;
    private int price;
    
    public MenuItem() {
        super.isCategory = false;
    };     
    
    public MenuItem(int itemId, int price){
        super.isCategory = false;
        this.itemId = itemId;
        this.price = price;
    }
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    
    public int getPrice(){
        return price;
    }
    
    public void setPrice(int price){
        this.price = price;
    }  
}
