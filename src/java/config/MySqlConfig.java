/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

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
   public static final String DB_NAME = "ezRest";
   public static final String DB_URL = "jdbc:mysql://localhost:" + BORIS_PORT;
   public static final String USERNAME = "root";
   public static final String PASSWORD = "root";
   
   // Tables
   public static abstract class Tables {
       public static final String EMPLOYEES = "employees";
       public static final String MENU_CATEGORIES = "MenuCategories";
       public static final String MENU_ITEMS = "MenuItems";
       public static final String ORDERS = "Orders";
       public static final String PERMISSIONS = "Permissions";
       public static final String VIP = "VIP";
       public static final String WORKING_HOURS = "WorkingHours";
       public static final String ORDER_ITEMS = "OrderItems";
              
       public static void build(String tableName) {        
           switch (tableName) {
               case EMPLOYEES:
                   buildEmployeesTable();
                   break;
               case MENU_CATEGORIES:
                   buildMenuCategoriesTable();
                   break;
               case MENU_ITEMS:
                   buildMenuItemsTable();
                   break;
               case ORDERS:
                   buildOrdersTable();
                   break;
               case PERMISSIONS:
                   buildPermissionsTable();
                   break;
               case VIP:
                   buildVipTable();
                   break;
               case WORKING_HOURS:
                   buildWorkingHoursTable();
                   break;
               case ORDER_ITEMS:
                   buildOrderItemsTable();
                   break;
               default:
                   break;
           }
       }
       
       private static void buildEmployeesTable() {
        String qString = "CREATE TABLE IF NOT EXISTS employees ("
          + "Employee_id int(11) NOT NULL AUTO_INCREMENT, "
          + "Permission_id int(11) NOT NULL, "
          + "First_Name varchar(15) COLLATE utf8_unicode_ci NOT NULL, "
          + "Last_Name varchar(15) COLLATE utf8_unicode_ci NOT NULL, "
          + "Position varchar(15) COLLATE utf8_unicode_ci NOT NULL, "
          + "Age int(11) NOT NULL, "
          + "Gender varchar(10) COLLATE utf8_unicode_ci NOT NULL, "
          + "City varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL, "
          + "Address varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL, "
          + "Email varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL, "
          + "Phone_Number varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL, "
          + "Password varchar(20) COLLATE utf8_unicode_ci NOT NULL, "
          + "PRIMARY KEY (Employee_id))";    
        
        MySqlUtils.updateQuery(qString);
       }
       
       private static void buildMenuCategoriesTable() {
        String qString = "CREATE TABLE IF NOT EXISTS MenuCategories ("
            + "Cat_id int(11) NOT NULL AUTO_INCREMENT, "
            + "Title varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
            + "Parent_id int(11) NOT NULL, "
            + "PRIMARY KEY (Cat_id))";
        
        MySqlUtils.updateQuery(qString);
       }
       
       private static void buildMenuItemsTable() {
          String qString = "CREATE TABLE IF NOT EXISTS MenuItems ("
            + "Item_id int(11) NOT NULL AUTO_INCREMENT, "
            + "Cat_id int(11) NOT NULL, "
            + "Title varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
            + "Price int(11) NOT NULL, "
            + "PRIMARY KEY (Item_id))";
          
          MySqlUtils.updateQuery(qString);
       }
       
       private static void buildOrdersTable() {
           String qString = "CREATE TABLE IF NOT EXISTS Orders ("
            + "Order_id int(11) NOT NULL AUTO_INCREMENT, "
            + "Employee_id int(11) NOT NULL, "
            + "Table_Num int(11) NOT NULL, "
            + "Order_Date date NOT NULL, "
            + "Total_sum int(11) NOT NULL, "
            + "PRIMARY KEY (Order_id))";
           
            MySqlUtils.updateQuery(qString);
       }
       
       private static void buildPermissionsTable() {
           String qString = "CREATE TABLE IF NOT EXISTS Permissions ("
            + "Permission_id int(11) NOT NULL AUTO_INCREMENT, "
            + "Title varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
            + "ADD_PRODUCT tinyint(1) NOT NULL, "
            + "ADD_EMPLOYEE tinyint(1) NOT NULL, "
            + "CANCEL_ORDER tinyint(1) NOT NULL, "
            + "ADD_DISCOUNT tinyint(1) NOT NULL, "
            + "EDIT_MENU tinyint(1) NOT NULL, "
            + "PRIMARY KEY (Permission_id))";
           
           MySqlUtils.updateQuery(qString);
       }
       
       private static void buildVipTable() {
           String qString = "CREATE TABLE IF NOT EXISTS VIP ("
            + "Id int(11) NOT NULL AUTO_INCREMENT, "
            + "First_Name varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
            + "Last_Name varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
            + "Birthday date NOT NULL, "
            + "Email varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, "
            + "PRIMARY KEY (Id))";
           
           MySqlUtils.updateQuery(qString);
       }
       
       private static void buildWorkingHoursTable() {
           String qString = "CREATE TABLE IF NOT EXISTS WorkingHours ("
            + "Record_id int(11) NOT NULL AUTO_INCREMENT, "
            + "Employee_id int(11) NOT NULL, "
            + "Clock_in timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
            + "Clock_out timestamp NULL DEFAULT NULL, "
            + "PRIMARY KEY (Record_id))";
           
           MySqlUtils.updateQuery(qString);
       }
       
       private static void buildOrderItemsTable() {
           String qString = "CREATE TABLE IF NOT EXISTS OrderItems ("
            + "Order_id int(11) NOT NULL, "
            + "Item_id int(11) NOT NULL, "
            + "Quantity int(11) NOT NULL)";
           
           MySqlUtils.updateQuery(qString);           
       }
   }
   
     // Database
    public static abstract class Database {
       public static void create() {
        String qString = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " ;";   
        
        try {
            Statement statement;
            Connection connection;

            Class.forName(MySqlConfig.JDBC_DRIVER);
            
            connection = DriverManager.getConnection(MySqlConfig.DB_URL, MySqlConfig.USERNAME, MySqlConfig.PASSWORD);

            statement = connection.createStatement();
            statement.executeUpdate(qString);
            
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error sending query: " + qString);
        }  catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    }
}
