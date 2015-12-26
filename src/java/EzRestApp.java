
import config.MySqlConfig;
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
        
        MySqlConfig.Tables.build(MySqlConfig.Tables.EMPLOYEES);
        MySqlConfig.Tables.build(MySqlConfig.Tables.MENU_CATEGORIES);
        MySqlConfig.Tables.build(MySqlConfig.Tables.MENU_ITEMS);
        MySqlConfig.Tables.build(MySqlConfig.Tables.ORDERS);
        MySqlConfig.Tables.build(MySqlConfig.Tables.ORDER_ITEMS);
        MySqlConfig.Tables.build(MySqlConfig.Tables.PERMISSIONS);
        MySqlConfig.Tables.build(MySqlConfig.Tables.VIP);
        MySqlConfig.Tables.build(MySqlConfig.Tables.WORKING_HOURS);
    }
}
