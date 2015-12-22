/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orderItems.dao;

import core.orderItems.OrderItem;
import java.util.List;

/**
 *
 * @author Shay
 */
public interface OrderItemsDao {
    List<OrderItem> getOrderItemsByOrderId(int orderId);
    void deleteOrderItemsByOrderId(int orderId);
    void insertOrderItems(List<OrderItem> newOrderItems);
}
