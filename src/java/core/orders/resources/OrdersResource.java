/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.resources;

import core.orders.Order;
import core.orders.dao.SqlOrdersDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.StringList;

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
    
    @GET
    @Path("/date/{date}")
    @Produces (MediaType.APPLICATION_JSON)
    public StringList getOrdersByDate(@PathParam("date") int date){
        StringList idList = ordersDao.getOrdersByDate(date);
        return idList;
    }
            
            
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public int addNewOrder(Order order) {
        ordersDao.createOrder(order);
        return ordersDao.getMaxId();
    }
    
    @POST
    @Path("/{orderId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMenuItem(@PathParam("orderId") int orderId, Order menuItem) {
        ordersDao.updateOrder(orderId, menuItem);
    }
    
    @POST
    @Path("/delete/{orderId}")
    public void deleteMenuItemById(@PathParam("orderId") int orderId) {
        ordersDao.deleteOrderById(orderId);
    }
}
