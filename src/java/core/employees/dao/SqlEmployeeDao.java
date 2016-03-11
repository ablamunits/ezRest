/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import config.MySqlConfig;
import core.employees.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringUtils;

/**
 *
 * @author borisa
 */
public class SqlEmployeeDao implements EmployeeDao {
    private final String[] columnNames = {
            "Employee_id",
            "Permission_id",
            "First_Name",
            "Last_Name",
            "Position",
            "Age",
            "Gender",
            "City",
            "Address",
            "Email",
            "Phone_Number",
            "Password"
        };
    
    @Override
    public Employee findEmployeeById(int id) {     
       ResultSet employeeSet = MySqlUtils.getQuery("SELECT * FROM " + MySqlConfig.Tables.EMPLOYEES + " WHERE Employee_id = " + id + ";");
       
       try {
           employeeSet.first();
           Employee employee = buildEmployee(employeeSet);
           employeeSet.close();
           return employee;
       } catch (SQLException ex) {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }

    @Override
    public void createEmployee(Employee employee) {           
        Object[] values = getObjectValues(employee);
        
        String qString = new StringBuilder("INSERT INTO " + MySqlConfig.Tables.EMPLOYEES)
                .append("(").append(StringUtils.arrayToString(this.columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();
        
        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void deleteEmployeeById(int id) {
        MySqlUtils.updateQuery("DELETE FROM " + MySqlConfig.Tables.EMPLOYEES + " WHERE Employee_id = " + id );
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        Object[] values = getObjectValues(employee);
        
        StringBuilder qString = new StringBuilder("UPDATE " + MySqlConfig.Tables.EMPLOYEES + " SET ");
        qString.append(MySqlUtils.updateSetString(this.columnNames, values))
               .append(" WHERE Employee_id=").append(id);
      
        System.out.println("update query:" + qString.toString());
        MySqlUtils.updateQuery(qString.toString());
    }
    
    @Override
    public ArrayList<Employee> getAllEmployees() {
       ResultSet employeesSet = MySqlUtils.getQuery("SELECT * FROM " + MySqlConfig.Tables.EMPLOYEES);
       
       try {
           ArrayList<Employee> employees = new ArrayList<Employee>() {};
           
           while(employeesSet.next()) {
               employees.add(buildEmployee(employeesSet));
           }
           
           return employees;
       } catch (SQLException ex) {
           Logger.getLogger(SqlEmployeeDao.class.getName()).log(Level.SEVERE, null, ex);
           return null;
       }
    }
    
    private Object[] getObjectValues(Employee employee) {
        Object[] values = {
            employee.getId() == 0 ? null : employee.getId(),
            employee.getPermissionId() == 0 ? null : employee.getPermissionId(), //
            employee.getFirstName(), ///
            employee.getLastName(), //
            employee.getPosition(),  //
            employee.getAge() == 0 ? null : employee.getAge(), //
            employee.getGender(), //
            employee.getCity(),
            employee.getAddress(),
            employee.getEmail(),  // 
            employee.getPhoneNumber(),
            employee.getPassword()   //
        };    
        
        return values;
    }
    
    private Employee buildEmployee(ResultSet employeeRow) throws SQLException
    {
        int id = employeeRow.getInt("Employee_id");
        int permissionId = employeeRow.getInt("Permission_id");
        String firstName = employeeRow.getString("First_Name");
        String lastName = employeeRow.getString("Last_Name");
        String position = employeeRow.getString("Position");
        int age = employeeRow.getInt("Age");
        String gender = employeeRow.getString("Gender");
        String city = employeeRow.getString("City");
        String address = employeeRow.getString("Address");
        String email = employeeRow.getString("Email");
        String phone = employeeRow.getString("Phone_Number");
        String password = employeeRow.getString("Password");
        
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setAge(age);
        employee.setPosition(position);
        employee.setPermissionId(permissionId);
        employee.setGender(Employee.Gender.valueOf(gender.toUpperCase()));
        employee.setCity(city);
        employee.setAddress(address);
        employee.setEmail(email);
        employee.setPhoneNumber(phone);
        employee.setPassword(password);

        return employee;
    }
}
