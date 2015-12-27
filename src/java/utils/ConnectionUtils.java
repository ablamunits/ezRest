/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author borisa
 */
public abstract class ConnectionUtils {
    public static void sendErrorResponse(HttpServletResponse response, String errorMessage) {
        try {
            response.setStatus(500);
            response.getWriter().println(errorMessage);
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
