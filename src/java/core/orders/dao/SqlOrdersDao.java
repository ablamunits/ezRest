/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.dao;

import config.MySqlConfig;
import core.orders.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringUtils;

/**
 *
 * @author Shay
 */
public class SqlOrdersDao implements OrdersDao {

    private final String[] columnNames = {
        "Order_id",
        "Employee_id",
        "Table_Num",
        "Order_Date",
        "Total_sum"
    };

    @Override
    public Order getOrderById(int orderId) {
        ResultSet orderSet = MySqlUtils.getQuery("SELECT * FROM Orders WHERE Order_id = " + orderId + ";");

        try {
            orderSet.first();
            Order order = buildOrder(orderSet);
            orderSet.close();
            return order;
        } catch (SQLException ex) {
            Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteOrderById(int orderId) {
        MySqlUtils.updateQuery("DELETE FROM Orders WHERE Order_id = " + orderId);
    }

    @Override
    public void createOrder(Order order) {
        Object[] values = getObjectValues(order);

        String qString = new StringBuilder("INSERT INTO Orders ")
                .append("(").append(StringUtils.arrayToString(columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();

        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void updateOrder(int orderId, Order order) {
        Object[] values = getObjectValues(order);

        StringBuilder qString = new StringBuilder("UPDATE " + MySqlConfig.Tables.ORDERS + " SET ");
        qString.append(MySqlUtils.updateSetString(this.columnNames, values))
                .append(" WHERE Order_id = ").append(orderId);

        System.out.println("update query:" + qString.toString());
        MySqlUtils.updateQuery(qString.toString());
    }

    private Order buildOrder(ResultSet orderSet) {
        try {
            int orderId = orderSet.getInt("Order_id");
            int employeeId = orderSet.getInt("Employee_id");
            int tableNum = orderSet.getInt("Table_Num");
            Date orderDate = orderSet.getDate("Order_date");
            int totalSum = orderSet.getInt("Total_sum");

            Order order = new Order();
            order.setOrderId(orderId);
            order.setEmployeeId(employeeId);
            order.setTableNum(tableNum);
            order.setOrderDate(orderDate);
            order.setTotalSum(totalSum);

            return order;
        } catch (SQLException ex) {
            Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Object[] getObjectValues(Order order) {
        Object[] values = {
            order.getOrderId() == 0 ? null : order.getOrderId(),
            order.getEmployeeId() == 0 ? null : order.getEmployeeId(),
            order.getTableNum() == 0 ? null : order.getTableNum(),
            order.getOrderDate(),
            order.getTotalSum()
        };

        return values;
    }

}
