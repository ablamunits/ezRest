/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders;

import java.text.DateFormat;

/**
 *
 * @author Shay
 */
//POJO
public class Order {
    private int orderId;
    private int employeeId;
    private int tableNum;
    private DateFormat orderDate;
    private int totalSum;
    
    public Order(int orderId, int employeeId, int tableNum, DateFormat orderDate, int totalSum){
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.tableNum = tableNum;
        this.totalSum = totalSum;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getTableNum() {
        return tableNum;
    }

    public DateFormat getOrderDate() {
        return orderDate;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public void setOrderDate(DateFormat orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }
}
