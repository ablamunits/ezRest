/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.dao;

import core.orders.Order;

/**
 *
 * @author Shay
 */
// CRUD -> Create, Retrieve, Update, Delete
public interface OrdersDao {
    Order getOrderById(int orderId);
    void deleteOrderById(int orderId);
    void createOrder(Order newOrder);
    void updateOrder(Order newOrder);
}
