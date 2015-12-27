/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees;

// Plain Java Object - "STAM" - POJO

public class Employee {
    public static enum Gender {
        MALE, FEMALE; 
    }
    private int id;
    private int permissionId;
    private String firstName;
    private String lastName;
    private String position;
    private int age;
    private Gender gender;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private String password;

    public Employee() {};

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
        
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
    
    public int getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public Gender getGender() {
        return this.gender;
    }
    
    public void setId(int emp_id) {
        id = emp_id;
    }
    
    public void setFirstName(String fName) {
        firstName = fName;
    }
    
    public void setLastName(String lName) {
        lastName = lName;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
