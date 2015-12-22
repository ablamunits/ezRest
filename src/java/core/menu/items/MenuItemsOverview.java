/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.items;

/**
 *
 * @author Shay
 */
public class MenuItemsOverview {
    String title;
    int itemId;
    int numOfTables;
    int quantity;
    
    public MenuItemsOverview(){};
    
    public MenuItemsOverview(String title, int itemId, int numOfTables, int quantity) {
        this.title = title;
        this.itemId = itemId;
        this.numOfTables = numOfTables;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public int getItemId() {
        return itemId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
