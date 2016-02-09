/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.dao;

import config.RedisConfig;
import core.employees.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import utils.RedisUtils;

/**
 *
 * @author borisa
 */
public class RedisEmployeeDao implements EmployeeDao {

    private final Jedis redisEmployeeDao;

    public RedisEmployeeDao() {
        redisEmployeeDao = new Jedis(RedisConfig.CONNECTION);
    }

    @Override
    public Employee findEmployeeById(int employeeId) {
        if (redisEmployeeDao.sismember("activeEmployees", String.valueOf(employeeId))) {
            List<String> employeeInfo = redisEmployeeDao.hmget("employee:" + employeeId, "permissionId", "firstName", "lastName", "position", "age", "gender", "city", "address", "email", "phoneNumber", "password");

            Employee employee = buildEmployee(employeeId, employeeInfo);

            return employee;
        } else {
            return null;
        }
    }

    @Override
    public void createEmployee(Employee employee) {
        int employeeId = employee.getId();

        if (redisEmployeeDao.exists("employee:" + employeeId) == false) {
            HashMap<String, String> employeeMap = buildEmployeeMap(employee);

            redisEmployeeDao.hmset("employee:" + employeeId, employeeMap);
            redisEmployeeDao.sadd("activeEmployees", String.valueOf(employeeId));
        }

    }

    @Override
    public void deleteEmployeeById(int employeeId) {
        redisEmployeeDao.del("employee:" + employeeId);
        redisEmployeeDao.srem("activeEmployees", String.valueOf(employeeId));
    }

    @Override
    public List<Employee> getAllEmployees() {
        Set<String> activeEmployeesSet = redisEmployeeDao.smembers("activeEmployees");
        ArrayList<Employee> activeEmployees = new ArrayList<Employee>() {
        };

        activeEmployeesSet.stream().forEach((employee) -> {
            activeEmployees.add(findEmployeeById(Integer.parseInt(employee)));
        });

        return activeEmployees;
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        //suppose if we want to update employee we will do it in sqlDataBase so probably dont need it
    }

    private HashMap<String, String> buildEmployeeMap(Employee employee) {
        HashMap<String, String> employeeMap = new HashMap<>();

        employeeMap.put("employeeId", String.valueOf(employee.getId()));
        employeeMap.put("permissionId", String.valueOf(employee.getPermissionId()));
        employeeMap.put("firstName", employee.getFirstName());
        employeeMap.put("lastName", employee.getLastName());
        employeeMap.put("position", employee.getPosition());
        employeeMap.put("age", String.valueOf(employee.getAge()));
        employeeMap.put("gender", employee.getGender().name());
//        employeeMap.put("city", employee.getCity());
//        employeeMap.put("address", employee.getAddress());
        employeeMap.put("email", employee.getEmail());
//        employeeMap.put("phoneNumber", employee.getPhoneNumber());
        employeeMap.put("password", employee.getPassword());

        return employeeMap;
    }

    private Employee buildEmployee(int employeeId, List<String> employeeInfo) {
        Employee employee = new Employee();

        employee.setId(employeeId);
        employee.setPermissionId(Integer.parseInt(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_PERMISSION_ID)));
        employee.setFirstName(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_FIRST_NAME));
        employee.setLastName(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_LAST_NAME));
        employee.setPosition(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_POSITION));
        employee.setAge(Integer.parseInt(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_AGE)));
        employee.setGender(Employee.Gender.valueOf(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_GENDER)));
        employee.setCity(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_CITY));
        employee.setAddress(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_ADDRESS));
        employee.setEmail(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_EMAIL));
        employee.setPhoneNumber(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_PHONE_NUMBER));
        employee.setPassword(employeeInfo.get(RedisUtils.Employee.EMPLOYEE_PASSWORD));

        return employee;
    }

}
