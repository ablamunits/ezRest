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
import utils.StringUtils;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import utils.StringList;

/**
 *
 * @author borisa
 */
public class SqlWorkingHoursDao implements WorkingHoursDao {

    private final String[] columnNames = {
        "Record_id",
        "Employee_id",
        "Clock_in",
        "Clock_out"
    };

    @Override
    public ArrayList<WorkingHours> getAllHoursForEmployee(int employeeId) {
        ResultSet workingHoursSet = MySqlUtils.getQuery("SELECT * FROM WorkingHours WHERE Employee_id = " + employeeId + ";");

        try {
            ArrayList<WorkingHours> workingHours = new ArrayList<WorkingHours>() {
            };

            while (workingHoursSet.next()) {
                workingHours.add(buildWorkingHours(workingHoursSet));
            }

            return workingHours;
        } catch (SQLException ex) {
            Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public WorkingHours getHoursForEmployeeByRecordId(int recordId) {
        ResultSet workingHoursSet = MySqlUtils.getQuery("SELECT * FROM WorkingHours WHERE Record_id = " + recordId + ";");

        try {
            workingHoursSet.first();
            WorkingHours workingHours = buildWorkingHours(workingHoursSet);
            workingHoursSet.close();

            return workingHours;
        } catch (SQLException ex) {
            Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String getDurationHoursForEmployeeByRecordId(int recordId) {
        ResultSet hoursReportSet = MySqlUtils.getQuery("SELECT TimeDiff(Clock_out, Clock_in) as Duration "
                                                     + "FROM WorkingHours "
                                                     + "WHERE Record_id = " + recordId);
        try {
            hoursReportSet.first();
                String durationHours = hoursReportSet.getString("Duration");
            hoursReportSet.close();
            
            return durationHours;
        } catch (SQLException ex) {
            Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public StringList getWorkingMonthlyHours(int employeeId, int month) {
        ResultSet hoursReportSet = MySqlUtils.getQuery("SELECT TimeDiff(Clock_out, Clock_in) as Duration "
                + "FROM WorkingHours "
                + "WHERE Employee_id = " + employeeId + " and MONTH(Clock_in) = " + month);
        try {
            ArrayList<String> monthlyHoursReport = new ArrayList<String>() {
            };

            while (hoursReportSet.next()) {
                monthlyHoursReport.add(hoursReportSet.getString("Duration"));
            }

            return new StringList(monthlyHoursReport);
        } catch (SQLException ex) {
            Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteWorkingHoursByRecordId(int recordId) {
        MySqlUtils.updateQuery("DELETE FROM WorkingHours WHERE Record_id = " + recordId);
    }

    @Override
    public int clockIn(int employeeId) {
        try {
            //        Object[] values = getObjectValues(recordId, employeeId);

            String qString = new StringBuilder("INSERT INTO WorkingHours (Employee_id) VALUES (" + employeeId + ")")
//                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
//                .append(" VALUES (")
//                .append(StringUtils.objectsArrayToString(values))
//                .append(")")
                    .toString();
            
            MySqlUtils.updateQuery(qString);
            ResultSet rs = MySqlUtils.getQuery("SELECT max(Record_id) FROM WorkingHours");
            rs.first();
            int recordId = rs.getInt(1);
            System.out.println(rs.getInt(1));
            return recordId;
//        return 1;
        } catch (SQLException ex) {
            Logger.getLogger(SqlWorkingHoursDao.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public void clockOut(int recordId, int employeeId) {
        MySqlUtils.updateQuery("UPDATE WorkingHours SET Clock_out = now() WHERE Record_id = " + recordId + " and Employee_id = " + employeeId);
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

//    private Object[] getObjectValues(int recordId, int employeeId) {
//        Object[] values = {
//            recordId,
//            employeeId,
//            new Timestamp(new java.util.Date().getTime()),
//            null
//        };
//
//        return values;
//    }
}
