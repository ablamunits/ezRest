
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author borisa
 */
@ApplicationPath("/api")
public class EzRestApp extends Application {
    public EzRestApp() {
        System.out.println("Initializing EzRest ...");
        // TODO: Create tables with CREATE IF NOT EXIST
    }
}
