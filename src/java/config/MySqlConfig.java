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
   public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   public static final String DB_URL = "jdbc:mysql://localhost:8889/ezRest";
   public static final String USERNAME = "root";
   public static final String PASSWORD = "root";
}
