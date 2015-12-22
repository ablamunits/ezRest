package core.workingHours.resources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import core.workingHours.WorkingHours;
import core.workingHours.dao.SqlWorkingHoursDao;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import utils.StringList;

/**
 *
 * @author borisa
 */
@Path("/workingHours")
public class WorkingHoursResource {
    @Context HttpServletResponse response;
    @Context HttpServletRequest request;
    
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
        System.out.println("Clock-in for session: " + request.getSession().getId());
        int recordId = workingHoursDao.clockIn(employeeId);
        if (recordId != -1)
            request.getSession().setAttribute("clockInId", recordId);
        else {
            try {
                response.getWriter().print("Clock in failed.");
                response.setStatus(500);
                response.flushBuffer();
            } catch (IOException ex) {
                Logger.getLogger(WorkingHoursResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    @POST
    @Path("/clockOut/{employeeId}")
    public void clockOut(@PathParam("employeeId") int employeeId) {
        int recordId = (int) request.getSession().getAttribute("clockInId");
        if (recordId > 0) {
            workingHoursDao.clockOut(recordId, employeeId);
            request.getSession().invalidate();
        }
        else {
            try {
                response.getWriter().print("Clock out failed.");
                response.setStatus(500);
                response.flushBuffer();
            } catch (IOException ex) {
                Logger.getLogger(WorkingHoursResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    @POST
    @Path("/delete/{recordId}")
    public void deleteRecord(@PathParam("recordId") int recordId) {
        workingHoursDao.deleteWorkingHoursByRecordId(recordId);
    }
    
}
