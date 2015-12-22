/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orderItems.dao;

import config.MySqlConfig;
import core.orderItems.OrderItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MySqlUtils;
import utils.StringUtils;

/**
 *
 * @author Shay
 */
public class SqlOrderItemsDao implements OrderItemsDao {

    private final String[] columnNames = {
        "Order_id",
        "Item_id",
        "Quantity",};

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        ResultSet orderedItemsSet = MySqlUtils.getQuery("SELECT * FROM " + MySqlConfig.Tables.ORDER_ITEMS + " WHERE Order_id = " + orderId + ";");

        try {
            ArrayList<OrderItem> orderedItems = new ArrayList<OrderItem>() {};

            while (orderedItemsSet.next()) {
                orderedItems.add(buildOrderItems(orderedItemsSet));
            }

            return orderedItems;
        } catch (SQLException ex) {
            Logger.getLogger(SqlOrderItemsDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteOrderItemsByOrderId(int orderId) {
        MySqlUtils.updateQuery("DELETE FROM " + MySqlConfig.Tables.ORDER_ITEMS + " WHERE Order_id = " + orderId);
    }

    @Override
    public void insertOrderItems(List<OrderItem> newOrderItems) {
        newOrderItems.stream().forEach((newItem) -> {
            addOrderItem(newItem);
        });
    }

    public void addOrderItem(OrderItem newOrder) {
        Object[] values = getObjectValues(newOrder);

        String qString = new StringBuilder("INSERT INTO " + MySqlConfig.Tables.ORDER_ITEMS)
                .append("(").append(StringUtils.arrayToString(this.columnNames)).append(")")
                .append(" VALUES (")
                .append(StringUtils.objectsArrayToString(values))
                .append(")")
                .toString();

        MySqlUtils.updateQuery(qString);
    }

    private Object[] getObjectValues(OrderItem orderItem) {
        Object[] values = {
            orderItem.getOrderId() == 0 ? null : orderItem.getOrderId(),
            orderItem.getItemId() == 0 ? null : orderItem.getItemId(),
            orderItem.getQuantity()
        };

        return values;
    }

    private OrderItem buildOrderItems(ResultSet orderItemRow) throws SQLException {
        int orderId = orderItemRow.getInt("Order_id");
        int itemId = orderItemRow.getInt("Item_id");
        int quantity = orderItemRow.getInt("Quantity");

        OrderItem orderItem = new OrderItem(orderId, itemId, quantity);

        return orderItem;
    }

}
