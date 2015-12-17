
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.resources;

import core.employees.Employee;
import core.employees.dao.SqlEmployeeDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// Resource for MySQL table - all employees
@Path("/employees")
public class EmployeeResource {    
    private final SqlEmployeeDao employeeDao;
    
    public EmployeeResource() {
        employeeDao = new SqlEmployeeDao();
    }
    
    // Get a list of all employees from db via GET request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewEmployee(Employee employee)
    {
        System.out.println(employee.getFirstName());
        employeeDao.createEmployee(employee);
    }
    
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployee() {
        // TODO:
        // 1. Get all the params of the employee, same as in addNewEmployee (above)
        // 2. Build new employee, same as above
        // 3. Send it to employeeDao.updateEmployee(employee).
        employeeDao.updateEmployee(null);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@PathParam("id") int id) {
        Employee employee = employeeDao.findEmployeeById(id);
        return employee;
    }
    
    @POST
    @Path("/delete/{id}")
    public void deleteEmployeeById(@PathParam("id") int id) {
        employeeDao.deleteEmployeeById(id);
    }
    
}
