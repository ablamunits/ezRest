/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import core.employees.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author borisa
 */
public class SqlEmployeeDao implements EmployeeDao {
    @Override
    public Employee findEmployeeById(int id) {     
       ResultSet employeeSet = MySqlUtils.getQuery("SELECT * FROM employees WHERE Employee_id = " + id + ";");
       
       try {
           employeeSet.first();
           Employee employee = buildEmployee(employeeSet);
           employeeSet.close();
           return employee;
       } catch (SQLException ex) {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }

    @Override
    public void createEmployee(Employee employee) {
        // INSERT INTO tbl_name (a,b,c) VALUES(1,2,3);
        // valueString() takes strings and turns them into ("string", "string", "string") for easier database usage
        String qString = "INSERT INTO employees "
                + "(Employee_id, Password, First_Name, Last_Name, Permission_id, Position, Age, Gender) "
                + "VALUES" + MySqlUtils.valueString(employee.getId(),
                                                    employee.getPassword(),
                                                    employee.getFirstName(),
                                                    employee.getLastName(),
                                                    employee.getPermissionId(),
                                                    employee.getPosition(),
                                                    employee.getAge(),
                                                    employee.getGender());

        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void deleteEmployeeById(int id) {
        MySqlUtils.updateQuery("DELETE FROM employees WHERE Employee_id = " + id );
    }

    @Override
    public void updateEmployee(Employee employee) {
        // TODO
        // 2 ways that this can be done:
        //      1. by doing SELECT by id from db, into a ResultSet, and updating the ResultSet (which will update the db I think)
        //      2. with a MySQL query directly, like in createEmployee
    }
    
    @Override
    public ArrayList<Employee> getAllEmployees() {
       ResultSet employeesSet = MySqlUtils.getQuery("SELECT * FROM employees;");
       
       try {
           ArrayList<Employee> employees = new ArrayList<Employee>() {};
           
           while(employeesSet.next()) {
               employees.add(buildEmployee(employeesSet));
           }
           
           return employees;
       } catch (SQLException ex) {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }
    
    private Employee buildEmployee(ResultSet employeeRow) throws SQLException
    {
        int id = employeeRow.getInt("Employee_id");
        String firstName = employeeRow.getString("First_Name");
        String lastName = employeeRow.getString("Last_Name");
        int permissionId = employeeRow.getInt("Permission_id");
        String position = employeeRow.getString("Position");
        int age = employeeRow.getInt("Age");
        String gender = employeeRow.getString("Gender");
        
        Employee employee = new Employee(id, firstName, lastName, null);
        employee.setAge(age);
        employee.setPosition(position);
        employee.setPermissionId(permissionId);
        employee.setGender(gender);

        return employee;
    }
}
