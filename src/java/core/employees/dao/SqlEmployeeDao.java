/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import core.employees.Employee;
import java.util.List;

/**
 *
 * @author borisa
 */
public class SqlEmployeeDao implements EmployeeDao {

    @Override
    public Employee findEmployeeById(int id) {
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
    
}
