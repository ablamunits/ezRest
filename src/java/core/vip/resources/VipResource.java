/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip.resources;

import core.vip.Vip;
import core.vip.dao.SqlVipDao;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("/vip")
public class VipResource {
    private final SqlVipDao vipDao;
    
    public VipResource() {
        vipDao = new SqlVipDao();
    }
    
    // Get a menu items by id from db via GET request
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vip getVipById(@PathParam("id") int id) {
        Vip vip = vipDao.getVipById(id);
        return vip;
    }
    
    // Add a new menu item to db via a POST request
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewVip(@QueryParam("id") int id,
                          @QueryParam("firstName") String firstName,
                          @QueryParam("lastName") String lastName,
                          @QueryParam("birthDay") String stringBirthDay,
                          @QueryParam("eMail") String eMail)
    {
        try {
            DateFormat birthDayDateFormat = new SimpleDateFormat(stringBirthDay);
            Date birthDay = (Date) birthDayDateFormat.parse(stringBirthDay);
            
            Vip newVip = new Vip(id, firstName, lastName, birthDay, eMail);

            vipDao.createVip(newVip);
        } catch (ParseException ex) {
            Logger.getLogger(VipResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateVip() {
        // TODO
        vipDao.updateVip(null);
    }
    
    @POST
    @Path("/delete/{id}")
    public void deleteMenuItemById(@PathParam("id") int id) {
        vipDao.deleteVipById(id);
    }
}
