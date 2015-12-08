/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import config.MySqlConfig;
import core.employees.dao.SqlEmployeeDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author borisa
 */
public class MySqlUtils {
    public static Connection connect() {
//           employeeSet.first();
//           return buildEmployee(employeeSet);
        Connection connection = null;
      
        try {
            Class.forName(MySqlConfig.JDBC_DRIVER);
            connection = DriverManager.getConnection(MySqlConfig.DB_URL, MySqlConfig.USERNAME, MySqlConfig.PASSWORD);
            return connection;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static ResultSet doQuery(String query) { 
        Statement statement = null;

        try {
            statement = MySqlUtils.connect().createStatement();
            return statement.executeQuery(query);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
}
