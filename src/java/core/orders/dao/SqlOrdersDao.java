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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringList;
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
        "Discount",
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
        } finally {
            try {
                orderSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    @Override
    public int getMaxId() {
        ResultSet orderSet = MySqlUtils.getQuery("SELECT max(Order_id) FROM Orders");
        int maxId;
        try {
            orderSet.first();
            maxId = orderSet.getInt("max(Order_id)");
            orderSet.close();
            return maxId;
        } catch (SQLException ex) {
            Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public StringList getOrdersByDate(int date) {
        String dateString = String.valueOf(date);
        
        int month = Integer.parseInt(dateString.substring(2, 4));
        int day = Integer.parseInt(dateString.substring(0, 2));
        int year = Integer.parseInt(dateString.substring(4, 8));

        ResultSet ordersIdSet = MySqlUtils.getQuery("SELECT Order_id FROM " + MySqlConfig.Tables.ORDERS
                + " WHERE MONTH(Order_Date)= " + month + " and DAY(Order_Date)= "
                + day + " and YEAR(Order_Date) = " + year + " ;");

        try {
            ArrayList<String> ordersId = new ArrayList<String>() {
            };

            while (ordersIdSet.next()) {
                ordersId.add(String.valueOf(ordersIdSet.getInt("Order_id")));
            }

            return new StringList(ordersId);
        } catch (SQLException ex) {
            Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                ordersIdSet.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Order buildOrder(ResultSet orderSet) {
        try {
            int orderId = orderSet.getInt("Order_id");
            int employeeId = orderSet.getInt("Employee_id");
            int tableNum = orderSet.getInt("Table_Num");
            Date orderDate = orderSet.getDate("Order_date");
            int discount = orderSet.getInt("Discount");
            int totalSum = orderSet.getInt("Total_sum");

            Order order = new Order();
            order.setOrderId(orderId);
            order.setEmployeeId(employeeId);
            order.setTableNum(tableNum);
            order.setOrderDate(orderDate);
            order.setDiscount(discount);
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
            order.getDiscount(),
            order.getTotalSum()
        };

        return values;
    }

}
