/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees;

import core.permissions.Permission;

// Plain Java Object - "STAM" - POJO

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private GenderEnum gender;
    private EmployeeInformation employeeInformation;
    
    public int getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public GenderEnum getGender() {
        return gender;
    }
    
    public EmployeeInformation getEmployeeInformation() {
        return employeeInformation;
    }
        
    public class EmployeeInformation {
        private int age;
        private String city;
        private String address;
        private String email;
        private String phoneNumber;
        private Permission permission;
    }
}
