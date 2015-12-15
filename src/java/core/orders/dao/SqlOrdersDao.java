/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.dao;

import core.orders.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;

/**
 *
 * @author Shay
 */
public class SqlOrdersDao implements OrdersDao {

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
        MySqlUtils.updateQuery("DELETE FROM Orders WHERE Order_id = " + orderId );
    }

    @Override
    public void createOrder(Order newOrder) {
        String qString = "INSERT INTO Orders "
                + "(Order_id, Employee_id, Table_Num, Order_Date, Total_sum) "
                + "VALUES " + MySqlUtils.valueString(newOrder.getOrderId(),
                                                     newOrder.getEmployeeId(),
                                                     newOrder.getTableNum(),
                                                     //check if order date is working
                                                     newOrder.getOrderDate(),
                                                     newOrder.getTotalSum());

        MySqlUtils.updateQuery(qString);
    }

    @Override
    public void updateOrder(Order newOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Order buildOrder(ResultSet orderSet) {
        try {
            int orderId = orderSet.getInt("Order_id");
            int employeeId = orderSet.getInt("Employee_id");
            int tableNum = orderSet.getInt("Table_Num");
            String stringOrderDate = orderSet.getString("Order_date");
            int totalSum = orderSet.getInt("Total_sum");
            
            DateFormat orderDate = new SimpleDateFormat(stringOrderDate);
            Order order = new Order(orderId, employeeId, tableNum, orderDate, totalSum);
           
            return order;
        } catch (SQLException ex) {
            Logger.getLogger(SqlOrdersDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
