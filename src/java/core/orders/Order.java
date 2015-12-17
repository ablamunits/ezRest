/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import utils.JsonDateAdapter;

/**
 *
 * @author Shay
 */
public class Order {
    private int orderId;
    private int employeeId;
    private int tableNum;
    private Date orderDate;
    private int totalSum;
    
    public Order() {}

    public int getOrderId() {
        return orderId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getTableNum() {
        return tableNum;
    }
    
    @XmlJavaTypeAdapter(JsonDateAdapter.class)
    public Date getOrderDate() {
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

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }
}
