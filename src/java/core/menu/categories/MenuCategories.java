/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menu.categories;

/**
 *
 * @author Shay
 */
public class MenuCategories {
    private int catId;
    private String title;
    private int parentId;
    
    private MenuCategories() {};
    public MenuCategories(int catId, String title, int parentId){
        this.catId = catId;
        this.title = title;
        this.parentId = parentId;
    }       

    public int getCatId() {
        return catId;
    }
    
    public void setCatId(int catId){
        this.catId = catId;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public int getParentId(){
        return parentId;
    }
    
    public void setParentId(int parentId){
        this.parentId = parentId;
    }  
}
