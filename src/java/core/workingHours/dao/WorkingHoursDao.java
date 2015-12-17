/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.workingHours.dao;

import core.workingHours.WorkingHours;
import java.util.List;

/**
 *
 * @author borisa
 */
public interface WorkingHoursDao {
    List <WorkingHours> getAllHoursForEmployee(int employeeId);
    void deleteWorkingHoursByRecordId(int recordId);
    void clockIn(int employeeId);
    void clockOut(int recordId, int employeeId);
}
