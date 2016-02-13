/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu;

/**
 *
 * @author borisa
 */
public abstract class MenuEntry {
    public String title;   
    public int categoryId;    
    public boolean isCategory;
    private int nextCategoryId = 0;
    
    //ADDED 12/02    
    public int itemId;  
    
    public int getItemId(){
        return itemId;
    }
    
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    //END ADDED 12/02    
    
    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
    
    public int getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public void setNextCategoryId(int nextCategoryId) {
        this.nextCategoryId = nextCategoryId;
    }
    
    public int getNextCategoryId() {
        return this.nextCategoryId;
    }
}