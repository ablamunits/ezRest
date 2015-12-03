/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import core.employees.Employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author borisa
 */
public class SqlEmployeeDao implements EmployeeDao {
    
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/ezrest";
   static final String USERNAME = "root";
   static final String PASSWORD = "1qa2wsmoshe";
   static final String EMP_INFO = "Employee_id, Permission_id, First_Name, Last_Name, Position, Age, Gender, City, Address, EMail, Phone_Number, Password, Bank_Information";
   
    @Override
    public Employee findEmployeeById(int id) {
      Connection conn = null;
      Statement stmt = null;
      
       try {
           Class.forName(JDBC_DRIVER);
           conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
           stmt = conn.createStatement();
           String getEmpSql;
           getEmpSql = "SELECT * FROM employee;";
           
           //getEmpSql = "SELECT " + EMP_INFO +" FROM employee WHERE id = " + id + ";";
           ResultSet empSet = stmt.executeQuery(getEmpSql);
           int emp_id;
           while (empSet.next())
           {
               emp_id = empSet.getInt("Employee_id");
           }
           //Maybe check if empSet is one
           //Validation
           return ConvertToEmployee(empSet);
       }
       catch (SQLException ex)
       {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
       }
        // Get from database ..
        // Convert to Employee Object ..
        // Return that employee ..
        return new Employee();
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
    
    Employee ConvertToEmployee(ResultSet empSet) throws SQLException
    {
        Employee desiredEmp = new Employee();
        
        desiredEmp.setFirstName(empSet.getString("First_Name"));
        //TODO
        return desiredEmp;
    }
}
