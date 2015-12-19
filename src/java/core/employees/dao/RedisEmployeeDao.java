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
public class RedisEmployeeDao implements EmployeeDao {

    @Override
    public Employee findEmployeeById(int id) {
        return null;
    }

    @Override
    public void createEmployee(Employee employee) {
        // TODO
    }

    @Override
    public void deleteEmployeeById(int id) {
        // TODO
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
