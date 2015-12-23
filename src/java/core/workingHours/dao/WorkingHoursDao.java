/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.workingHours.dao;

import core.workingHours.WorkingHours;
import java.util.List;
import utils.StringList;

/**
 *
 * @author borisa
 */
public interface WorkingHoursDao {
    List<WorkingHours> getAllHoursForEmployee(int employeeId);
    StringList getWorkingMonthlyHours(int employeeId, int month);
    String getDurationHoursForEmployeeByRecordId(int recordId);
    WorkingHours getHoursForEmployeeByRecordId(int recordId);
    void deleteWorkingHoursByRecordId(int recordId);
    int clockIn(int employeeId);
    void clockOut(int recordId, int employeeId);
}
