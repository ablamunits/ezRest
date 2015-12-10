/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.menumanager;

/**
 *
 * @author Shay
 */
public class SubMenu {
    String title;
    int nextCategory;
    public static final int NULL = -1;
    
    public SubMenu(){};
    
    public SubMenu(String title, int nextCategory){
        this.title = title;
        this.nextCategory = nextCategory;
    }
    
    public String getTitle(){
        return title;
    }
    
    public int getNextCategory(){
        return nextCategory;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setNextCategory(int nextCategory){
        this.nextCategory = nextCategory;
    }
}
