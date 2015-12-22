/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orderItems.resources;

import core.orderItems.OrderItem;
import core.orderItems.dao.SqlOrderItemsDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Shay
 */
@Path("/orderItems")
public class OrderItemsResource {
    private final SqlOrderItemsDao orderItemsDao;
    
    public OrderItemsResource() {
        orderItemsDao = new SqlOrderItemsDao();
    }
    
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderItem> getOrderItemsByOrderId(@PathParam("orderId") int orderId) {
        return orderItemsDao.getOrderItemsByOrderId(orderId);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
        public void addNewOrderItems(List<OrderItem> orderItems) {
        orderItemsDao.insertOrderItems(orderItems);
    }
    
    @POST
    @Path("/delete/{orderId}")
    public void deleteOrderItemsByOrderId(@PathParam("orderId") int orderId) {
        orderItemsDao.deleteOrderItemsByOrderId(orderId);
    }
}
