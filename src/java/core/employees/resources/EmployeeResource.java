
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
import javax.ws.rs.QueryParam;
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
        List<Employee> employees = employeeDao.getAllEmployees();
        return employees;
    }

    // Add a new employee to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewEmployee(@QueryParam("id") int id,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName,
                               @QueryParam("gender") String gender,
                               @QueryParam("permissionId") int permissionId,
                               @QueryParam("age") int age,
                               @QueryParam("email") String email,
                               @QueryParam("position") String position,
                               @QueryParam("phoneNumber") String phoneNumber,
                               @QueryParam("password") String password)
    {
        Employee newEmployee = new Employee(id, firstName, lastName, gender);
        
        newEmployee.setPassword(password);
        newEmployee.setPermissionId(permissionId);
        newEmployee.setAge(age);
        newEmployee.setEmail(email);
        newEmployee.setPosition(position);
        newEmployee.setPhoneNumber(phoneNumber);

        employeeDao.createEmployee(newEmployee);
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
