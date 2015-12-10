/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menumanager.menuitems;

/**
 *
 * @author Shay
 */
public class MenuItems {
    private int itemId;
    private String title;
    private int catId;
    private int price;
    
    private MenuItems() {};
    public MenuItems(int itemId, String title, int catId, int price){
        this.itemId = itemId;
        this.title = title;
        this.catId = catId;
        this.price = price;
    }       

    public int getCatId() {
        return itemId;
    }
    
    public void setCatId(int itemId){
        this.itemId = itemId;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public int getParentId(){
        return catId;
    }
    
    public void setParentId(int catId){
        this.catId = catId;
    }  
    
    public int getPrice(){
        return price;
    }
    
    public void setPrice(int price){
        this.price = price;
    }  
}
