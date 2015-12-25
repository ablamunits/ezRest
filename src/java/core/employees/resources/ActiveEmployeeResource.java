package core.employees.resources;

import core.employees.Employee;
import core.employees.dao.RedisEmployeeDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// Active employees
// Get the clocked-in employees (who is working now?) - Redis
@Path("/employees/active")
public class ActiveEmployeeResource {

    private final RedisEmployeeDao employeeDao;

    public ActiveEmployeeResource() {
        employeeDao = new RedisEmployeeDao();
    }
    
    @GET
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployeeById(@PathParam("employeeId") int employeeId) {
        return employeeDao.findEmployeeById(employeeId);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllActiveEmployees() {
        return employeeDao.getAllEmployees();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addActiveEmployee(Employee employee) {
        employeeDao.createEmployee(employee);
    }
    
    @POST
    @Path("/delete/{employeeId}")
    public void deleteActiveEmployeeById(@PathParam("employeeId") int employeeId) {
        employeeDao.deleteEmployeeById(employeeId);
    }
}
