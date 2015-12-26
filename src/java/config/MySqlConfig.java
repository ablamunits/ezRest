/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author borisa
 */
public class MySqlConfig {
    /* 
    Change the needed settings such as DB_URL, USERNAME, PASSWORD if needed.
    */
   public static final String BORIS_PORT = "8889";  
   public static final String SHAY_PORT = "3306";  
   public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   public static final String DB_URL = "jdbc:mysql://localhost:" + BORIS_PORT + "/ezRest";
   public static final String USERNAME = "root";
   public static final String PASSWORD = "root";
   
   // Table names
   public class Tables {
       public static final String EMPLOYEES = "employees";
       public static final String MENU_CATEGORIES = "MenuCategories";
       public static final String MENU_ITEMS = "MenuItems";
       public static final String ORDERS = "Orders";
       public static final String PERMISSIONS = "Permissions";
       public static final String VIP = "VIP";
       public static final String WORKING_HOURS = "WorkingHours";
       public static final String ORDER_ITEMS = "OrderItems";
   }
}
