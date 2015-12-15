/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.orders.resources;

import core.orders.Order;
import core.orders.dao.SqlOrdersDao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        Order order = ordersDao.getOrderById(orderId);
        return order;
    }
    
    // Add a new menu item to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewOrder(@QueryParam("orderId") int orderId,
                            @QueryParam("employeeId") int employeeId,
                            @QueryParam("tableNum") int tableNum,
                            @QueryParam("orderDate") String stringOrderDate,
                            @QueryParam("totalSum") int totalSum)
    {
        DateFormat orderDate = new SimpleDateFormat(stringOrderDate);
        Order newOrder = new Order(orderId, employeeId, tableNum, orderDate, totalSum);

        ordersDao.createOrder(newOrder);
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
