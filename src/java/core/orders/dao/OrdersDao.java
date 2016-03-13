/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.dao;

import core.orders.Order;
import java.util.List;
import utils.StringList;

/**
 *
 * @author Shay
 */
public interface OrdersDao {
    Order getOrderById(int orderId);
    void deleteOrderById(int orderId);
    void createOrder(Order newOrder);
    void updateOrder(int orderId, Order order);
    int getMaxId();
    StringList getOrdersByDate(int date);
}
