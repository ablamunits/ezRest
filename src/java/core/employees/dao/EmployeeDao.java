/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import core.employees.Employee;

public interface EmployeeDao {
    Employee getEmployeeById(int id);
    void addEmployee(Employee employee);
}
