/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.vip.resources;

import core.vip.Vip;
import core.vip.dao.SqlVipDao;
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
@Path("/vip")
public class VipResource {
    private final SqlVipDao vipDao;
    
    public VipResource() {
        vipDao = new SqlVipDao();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Vip getVipById(@PathParam("id") int id) {
        Vip vip = vipDao.getVipById(id);
        return vip;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewVip(Vip vip)
    {
        vipDao.createVip(vip);
    }
    
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateVip(@PathParam("id") int id, Vip vip) {
        vipDao.updateVip(id, vip);
    }
    
    @POST
    @Path("/delete/{id}")
    public void deleteMenuItemById(@PathParam("id") int id) {
        vipDao.deleteVipById(id);
    }
}
