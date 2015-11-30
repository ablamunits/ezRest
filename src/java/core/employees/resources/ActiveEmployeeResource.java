package core.employees.resources;

import core.employees.Employee;
import core.employees.dao.RedisEmployeeDao;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllActiveEmployees() {
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        return "ALL ACTIVE EMPLOYEES FROM REDIS :D";
    }
}
