package core.workingHours.resources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import core.workingHours.WorkingHours;
import core.workingHours.dao.SqlWorkingHoursDao;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author borisa
 */
@Path("/workingHours")
public class WorkingHoursResource {
    private final SqlWorkingHoursDao workingHoursDao;
    
    public WorkingHoursResource() {
        workingHoursDao = new SqlWorkingHoursDao();
    }
    
    @GET
    @Path("/{employeeId}")
    public List<WorkingHours> getEmployeeWorkingHours(@PathParam("employeeId") int employeeId) {
        return workingHoursDao.getAllHoursForEmployee(employeeId);
    }
    
    @POST
    @Path("/clockIn/{employeeId}")
    public void clockIn(@PathParam("employeeId") int employeeId) {
        // Should maybe store the record id in a session?...
        workingHoursDao.clockIn(employeeId);
    }
    
    @POST
    @Path("/clockOut/{employeeId}")
    public void clockOut(@PathParam("employeeId") int employeeId) {
        // Should maybe check if a clock-in exists in a session?...
        int recordId = 2; // This is a test recordId. Should be taken from a session.
        workingHoursDao.clockOut(recordId, employeeId);
    }
    
    @POST
    @Path("/delete/{recordId}")
    public void deleteRecord(@PathParam("recordId") int recordId) {
        workingHoursDao.deleteWorkingHoursByRecordId(recordId);
    }
}
