/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import core.employees.Employee;
import core.permissions.Permission;
import core.permissions.dao.SqlPermissionDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import authentication.AuthObject;
import config.MySqlConfig;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import utils.MySqlUtils;
import utils.SessionUtils;

/**
 *
 * @author borisa
 */
@Path("/auth")
public class AuthResource {    
    
    @Context 
    HttpServletResponse response;
    @Context
    HttpServletRequest request;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject isLoggedIn() {
        JSONObject result = new JSONObject();
        HttpSession session;
        response.setContentType("application/json");
        
        session = request.getSession(false);
        try {
            if (session != null && (boolean) session.getAttribute(SessionUtils.IS_LOGGED_IN) == true) {
                System.out.println("logged in!");
                    result.put("loggedIn", true)
                          .put("email", session.getAttribute(SessionUtils.USER_EMAIL))
                          .put("employeeId", session.getAttribute(SessionUtils.USER_ID))
                          .put("firstName", session.getAttribute(SessionUtils.USER_FIRST_NAME))
                          .put("lastName", session.getAttribute(SessionUtils.USER_LAST_NAME));
            }
            else {
                System.out.println("not logged in!");
                // Report error in the form of an object to see whats wrong..
                result.put("loggedIn", false);
            }
                response.getWriter().print(result);
                response.flushBuffer();
        }   
        catch (JSONException | IOException ex) {
            Logger.getLogger(AuthResource.class.getName()).log(Level.SEVERE, null, ex);
            response.setStatus(500);
        }
        return null;
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public void doLogin(AuthObject auth){
        String qString = "SELECT * FROM " + MySqlConfig.Tables.EMPLOYEES + " WHERE Email LIKE \"" + auth.getEmail() + "\"";
        System.out.println(qString);
        ResultSet result = MySqlUtils.getQuery(qString);
        
        try {
            result.first();
            String email = result.getString("Email");
            String password = result.getString("Password");
            String uid = result.getString("Employee_id");
            String firstName = result.getString("First_Name");
            String lastName = result.getString("Last_Name");
            
            if (auth.getPassword().equals(password) && auth.getEmail().equals(email)) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionUtils.USER_EMAIL, email);
                session.setAttribute(SessionUtils.IS_LOGGED_IN, true);
                session.setAttribute(SessionUtils.USER_ID, uid);
                session.setAttribute(SessionUtils.USER_FIRST_NAME, firstName);
                session.setAttribute(SessionUtils.USER_LAST_NAME, lastName);
                response.setStatus(200);
            }
        } catch (SQLException ex) {
            try {
                Logger.getLogger(AuthResource.class.getName()).log(Level.SEVERE, null, ex);
                response.setStatus(500);
                response.getWriter().print("Login failed.");
                response.flushBuffer();
            } catch (IOException ex1) {
                Logger.getLogger(AuthResource.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public void doLogout(){
        if (request.getSession(false) != null) {
            request.getSession(false).setAttribute(SessionUtils.IS_LOGGED_IN, false);
        }
    }
}
