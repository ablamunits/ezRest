/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.tables.resources;

import core.tables.SingleTableOrder;
import core.tables.Table;
import core.tables.dao.RedisTableDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import utils.ConnectionUtils;

/**
 *
 * @author borisa
 */
@Path("/tables")
public class TableResource {
    @Context HttpServletResponse response;
    @Context HttpServletRequest request;
    
    private final RedisTableDao tableDao;
    
    public TableResource() {
        tableDao = new RedisTableDao();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Table> getAllTables() {
        return tableDao.getAllTables();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Table getTableById(@PathParam("id") int id) {
        return tableDao.getTableById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewTable(Table table)
    {
        if (isValidTableCreationEntry(table))
            tableDao.createTable(table);
        else {
            ConnectionUtils.sendErrorResponse(response, "Invalid input provided");
        }          
    }
    
    @POST
    @Path("/makeOrder/{tableId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void makeOrderForTable(@PathParam("tableId") int tableId, List<SingleTableOrder> newOrder) {
        tableDao.updateTableOrder(tableId, newOrder);
    }
    
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTable(@PathParam("id") int id, Table table) {
        tableDao.updateTable(id, table);
    }
    
    @POST
    @Path("/delete/{id}")
    public void deleteTableById(@PathParam("id") int id) {
        tableDao.deleteTableById(id);
    }
    
    private boolean isValidTableCreationEntry(Table table) {
        return table.getId() != 0 && table.getNumOfGuests() != 0 && table.getServerId() != 0;
    }
}
