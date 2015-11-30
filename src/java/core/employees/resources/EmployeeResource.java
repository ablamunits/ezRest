
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.employees.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/employees")
public class EmployeeResource {
@GET
@Produces(MediaType.APPLICATION_JSON)
    public String testFunction() throws JSONException {
        JSONObject test = new JSONObject();
        
        test.put("id", "2").put("name", "Jerby");
        
        return test.toString();
    }
    
    @GET
    @Produces("text/plain")
    @Path("/boris")
    public String testFunction2() {
        return "Hello boris";
    }

}
