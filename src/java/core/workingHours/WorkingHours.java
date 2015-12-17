/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.workingHours;

/**
 *
 * @author borisa
 */
public class WorkingHours {
    private int recordId;
    private int employeeId;
    private String clockInTimestamp;
    private String clockOutTimestamp;
    
    public WorkingHours() {}
    
    public int getRecordId() {
        return recordId;
    }
    
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getClockInTimestamp() {
        return clockInTimestamp;
    }

    public void setClockInTimestamp(String clockInTimestamp) {
        this.clockInTimestamp = clockInTimestamp;
    }

    public String getClockOutTimestamp() {
        return clockOutTimestamp;
    }

    public void setClockOutTimestamp(String clockOutTimestamp) {
        this.clockOutTimestamp = clockOutTimestamp;
    }
    
    
}
