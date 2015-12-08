/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import core.employees.Employee;
import core.employees.GenderEnum;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author borisa
 */
public class SqlEmployeeDao implements EmployeeDao {
    
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:8889/ezRest";
   static final String USERNAME = "root";
   static final String PASSWORD = "root";
//      static final String PASSWORD = "1qa2wsmoshe";
   
    @Override
    public Employee findEmployeeById(int id) {
        
       ResultSet employeeSet = MySqlUtils.doQuery("SELECT * FROM employees WHERE Employee_id = " + id + ";");
       
       try {
           employeeSet.first();
           return buildEmployee(employeeSet);
       } catch (SQLException ex) {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }

    @Override
    public void createEmployee(Employee employee) {
        // Take an employee object ..
        // Create a row in the table ..
    }

    @Override
    public void deleteEmployeeById(int id) {
        // Delete row from the table ..
    }

    @Override
    public void updateEmployee(Employee employee) {
        // Update row in table ..
    }
    
    @Override
    public List<Employee> getAllEmployees() {
        // Connect to db, get all employees as json ..
        // Return as list.
        return null;
    }
    
    private Employee buildEmployee(ResultSet employeeRow) throws SQLException
    {
        int id = employeeRow.getInt("Employee_id");
        String firstName = employeeRow.getString("First_Name");
        String lastName = employeeRow.getString("Last_Name");
        int permissionId = employeeRow.getInt("Permission_id");
        String position = employeeRow.getString("Position");
        int age = employeeRow.getInt("Age");
        GenderEnum gender = GenderEnum.valueOf(employeeRow.getString("Gender").toUpperCase());
        
        Employee e = new Employee(id, firstName, lastName, null);
        e.setAge(age);
        e.setPosition(position);
        e.setPermissionId(permissionId);
        e.setGender(gender);
        //TODO
        return e;
    }
}
