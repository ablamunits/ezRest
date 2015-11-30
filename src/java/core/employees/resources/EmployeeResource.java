
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.resources;

import core.employees.Employee;
import core.employees.dao.SqlEmployeeDao;
import java.util.List;
import javax.ws.rs.GET;
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
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeDao.getAllEmployees();
        return employees;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@PathParam("id") int id) {
        Employee employee = employeeDao.findEmployeeById(id);
        return employee;
    }
}

// Examples
// --------
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String testFunction() throws JSONException {
//        JSONObject test = new JSONObject();
//        
//        test.put("id", "2").put("name", "Jerby");
//        
//        return test.toString();
//    }
//    
//    @GET
//    @Produces("text/plain")
//    @Path("/boris")
//    public String testFunction2() {
//        return "Hello boris";
//    }
