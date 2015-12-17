/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.workingHours.dao;

import core.workingHours.WorkingHours;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author borisa
 */
public class SqlWorkingHoursDao implements WorkingHoursDao {
    
    @Override
    public List<WorkingHours> getAllHoursForEmployee(int employeeId) {
        ResultSet workingHoursSet = MySqlUtils.getQuery("SELECT * FROM WorkingHours WHERE Employee_id = " + employeeId + ";");

       try {
           ArrayList<WorkingHours> workingHours = new ArrayList<WorkingHours>() {};
           
           while(workingHoursSet.next()) {
               workingHours.add(buildWorkingHours(workingHoursSet));
           }
           
           return workingHours;
       } catch (SQLException ex) {
           Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }

    @Override
    public void deleteWorkingHoursByRecordId(int recordId) {
        MySqlUtils.updateQuery("DELETE FROM WorkingHours WHERE Record_id = " + recordId );
    }

    @Override
    public void clockIn(int employeeId) {
        MySqlUtils.updateQuery("INSERT INTO WorkingHours (Employee_id) VALUES (" + employeeId + ")");
    }

    @Override
    public void clockOut(int recordId, int employeeId) {
        MySqlUtils.updateQuery("UPDATE WorkingHours SET Clock_out=now() WHERE Record_id = " + recordId);
    }

    private WorkingHours buildWorkingHours(ResultSet workingHoursSet) {
        try {
            int recordId = workingHoursSet.getInt("Record_id");
            int employeeId = workingHoursSet.getInt("Employee_id");
            String clockInTimestamp = workingHoursSet.getString("Clock_in");
            String clockOutTimestamp = workingHoursSet.getString("Clock_out");
            
            WorkingHours workingHours = new WorkingHours();
            workingHours.setRecordId(recordId);
            workingHours.setEmployeeId(employeeId);
            workingHours.setClockInTimestamp(clockInTimestamp);
            workingHours.setClockOutTimestamp(clockOutTimestamp);
            
            return workingHours;
        } catch (SQLException ex) {
            Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
