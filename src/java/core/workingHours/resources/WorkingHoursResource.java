package core.workingHours.resources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import core.workingHours.WorkingHours;
import core.workingHours.dao.SqlWorkingHoursDao;
import java.io.IOException;
import java.util.HashMap;
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
import org.json.JSONException;
import org.json.JSONObject;
import utils.ConnectionUtils;
import utils.StringList;

/**
 *
 * @author borisa
 */
@Path("/workingHours")
public class WorkingHoursResource {

    @Context
    HttpServletResponse response;
    @Context
    HttpServletRequest request;

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
    @Path("/record/{recordId}")
    public WorkingHours getEmployeeWorkingHoursByRecordId(@PathParam("recordId") int recordId) {
        return workingHoursDao.getHoursForEmployeeByRecordId(recordId);
    }

    @GET
    @Path("/durationRecord/{recordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getEmployeeWorkingHoursDurationByRecordId(@PathParam("recordId") int recordId) {
        JSONObject res = new JSONObject();
        try {
            String workingHours = workingHoursDao.getDurationHoursForEmployeeByRecordId(recordId);
            res.put("duration", workingHours);
            response.getWriter().print(res);
            response.flushBuffer();
        } catch (JSONException ex) {
            Logger.getLogger(WorkingHoursResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WorkingHoursResource.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @GET
    @Path("/durationMonth/{employeeId}") //durationMonth/{employeeId}?month={month[1-12]}
    @Produces(MediaType.APPLICATION_JSON)
    public StringList getEmployeeWorkingHoursDurationByMonth(@PathParam("employeeId") int employeeId,
            @QueryParam("month") int month) {
        return workingHoursDao.getWorkingMonthlyHours(employeeId, month);
    }

    @POST
    @Path("/clockIn/{employeeId}")
    public void clockIn(@PathParam("employeeId") int employeeId) {
        System.out.println("Clock-in for session: " + request.getSession().getId());
        HashMap<Integer, Integer> clockInEmployees;
        int recordId = workingHoursDao.clockIn(employeeId);

        if (recordId != -1) {
            clockInEmployees = (HashMap<Integer, Integer>) request.getSession().getAttribute("clockInId");
            clockInEmployees = updateMap(clockInEmployees, employeeId, recordId);
            request.getSession().setAttribute("clockInId", clockInEmployees);
        } else {
            ConnectionUtils.sendErrorResponse(response, "Clock in failed!");
        }

    }

    @POST
    @Path("/clockOut/{employeeId}")
    public void clockOut(@PathParam("employeeId") int employeeId) {
        HashMap<Integer, Integer> clockInEmployees = (HashMap<Integer, Integer>) request.getSession().getAttribute("clockInId");
        int recordId = clockInEmployees.get(employeeId);
        
        if (recordId > 0) {
            workingHoursDao.clockOut(recordId, employeeId);
            clockInEmployees.remove(employeeId);
            request.getSession().setAttribute("clockInId", clockInEmployees);
        } else {
            ConnectionUtils.sendErrorResponse(response, "Clock out failed!");
        }
    }

    @POST
    @Path("/delete/{recordId}")
    public void deleteRecord(@PathParam("recordId") int recordId) {
        workingHoursDao.deleteWorkingHoursByRecordId(recordId);
    }

    private HashMap<Integer, Integer> updateMap(HashMap<Integer, Integer> clockInEmployees, int employeeId, int recordId) {
        if (clockInEmployees == null) {
            clockInEmployees = new HashMap<>();
        }
        clockInEmployees.put(employeeId, recordId);

        return clockInEmployees;
    }

}
