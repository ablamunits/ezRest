/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees;

// Plain Java Object - "STAM" - POJO

public class Employee {
    private int id;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;

    private int age;
    private int permissionId;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private String position;

    private Employee() {};
    public Employee(int id, String firstName, String lastName, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
    
    public String getGender() {
        return gender;
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
    
    public void setGender(String gender) {
        this.gender = gender;
    }
}
