/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import core.employees.Employee;
import java.util.List;

// CRUD -> Create, Retrieve, Update, Delete
public interface EmployeeDao {
    List<Employee> getAllEmployees();
    Employee findEmployeeById(int id);
    void createEmployee(Employee employee);
    void deleteEmployeeById(int id);
    void updateEmployee(int id, Employee employee);
}
