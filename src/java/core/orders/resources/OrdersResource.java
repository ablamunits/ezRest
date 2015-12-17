/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.resources;

import core.orders.Order;
import core.orders.dao.SqlOrdersDao;
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
@Path("/orders")
public class OrdersResource {
    private final SqlOrdersDao ordersDao;
    
    public OrdersResource() {
        ordersDao = new SqlOrdersDao();
    }
    
    // Get a menu items by id from db via GET request
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrderById(@PathParam("orderId") int orderId) {
        return ordersDao.getOrderById(orderId);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewOrder(Order order) {
        ordersDao.createOrder(order);
    }
    
    @POST
    @Path("/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMenuItem() {
        // TODO
        ordersDao.updateOrder(null);
    }
    
    @POST
    @Path("/delete/{orderId}")
    public void deleteMenuItemById(@PathParam("orderId") int orderId) {
        ordersDao.deleteOrderById(orderId);
    }
}
