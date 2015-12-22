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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import utils.StringList;

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
    public List<WorkingHours> getEmployeeAllWorkingHours(@PathParam("employeeId") int employeeId) {
        return workingHoursDao.getAllHoursForEmployee(employeeId);
    }
    
    @GET
    @Path("/record/{employeeId}")  //record/{employeeId}?recordId={recordId}
    public WorkingHours getEmployeeWorkingHoursByRecordId(@PathParam("employeeId") int employeeId,
                                                          @QueryParam("recordId")  int recordId) {
        return workingHoursDao.getHoursForEmployeeByRecordId(employeeId, recordId);
    }
    
    @GET
    @Path("/durationRecord/{employeeId}")  //durationRecord/{employeeId}?recordId={recordId}
    public String getEmployeeWorkingHoursDurationByRecordId(@PathParam("employeeId") int employeeId,
                                                            @QueryParam("recordId")  int recordId) {
        return workingHoursDao.getDurationHoursForEmployeeByRecordId(employeeId, recordId);
    }
    
    @GET
    @Path("/durationMonth/{employeeId}") //durationMonth/{employeeId}?month={month[1-12]}
    @Produces(MediaType.APPLICATION_JSON)
    public StringList getEmployeeWorkingHoursDurationByMonth(@PathParam("employeeId") int employeeId, 
                                                             @QueryParam("month")     int month) {
        return workingHoursDao.getWorkingMonthlyHours(employeeId, month);
    }
    
    @POST
    @Path("/clockIn/{employeeId}")
    public void clockIn(@PathParam("employeeId") int employeeId) {
        // Should maybe store the record id in a session?...
        int recordId = 33;
        workingHoursDao.clockIn(recordId, employeeId);
    }
    
    @POST
    @Path("/clockOut/{employeeId}")
    public void clockOut(@PathParam("employeeId") int employeeId) {
        // Should maybe check if a clock-in exists in a session?...
        int recordId = 33; // This is a test recordId. Should be taken from a session.
        workingHoursDao.clockOut(recordId, employeeId);
    }
    
    
    @POST
    @Path("/delete/{recordId}")
    public void deleteRecord(@PathParam("recordId") int recordId) {
        workingHoursDao.deleteWorkingHoursByRecordId(recordId);
    }
    
}
