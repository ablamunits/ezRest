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
        Connection connection;
      
        try {
            Class.forName(MySqlConfig.JDBC_DRIVER);
            connection = DriverManager.getConnection(MySqlConfig.DB_URL, MySqlConfig.USERNAME, MySqlConfig.PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Exception in MySqlUtils");
            return null;
        }
    }
    
    public static ResultSet getQuery(String query) { 
        Statement statement;
        Connection connection;
        ResultSet result;

        try {
            connection = connect();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            
            return result;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
    public static void updateQuery(String query) {
        try {
            Statement statement;
            Connection connection;
            
            connection = connect();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error sending query: " + query);
        }
    }
    
    public static String valueString(Object... values) {
        StringBuilder queryString = new StringBuilder("(");
        
        for (int i=0; i < values.length; i++) {
            queryString.append(values[i]);
            if (i == values.length - 1)
                queryString.append(")");
            else
                queryString.append(",");
        }
        
        return queryString.toString();
    }
    
    public static String updateSetString(String[] columnNames, Object[] values) {
        StringBuilder qString = new StringBuilder();
        
        for (int i = 0; i < columnNames.length; i++) {
            if (values[i] != null) {
                qString.append(columnNames[i])
                    .append("=\"")
                    .append(values[i]).append("\"");
                
                if(i < columnNames.length - 1)
                    qString.append(",");
            }
        }
        
        qString.setLength(qString.length() - 1); // Trim last ',' char
        
        return qString.toString();
    }
}
