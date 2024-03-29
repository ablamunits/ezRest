/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author borisa
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);

        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(authentication.AuthResource.class);
        resources.add(core.employees.resources.ActiveEmployeeResource.class);
        resources.add(core.employees.resources.EmployeeResource.class);
        resources.add(core.menu.categories.resources.MenuCategoriesResource.class);
        resources.add(core.menu.items.resources.MenuItemsResource.class);
        resources.add(core.orderItems.resources.OrderItemsResource.class);
        resources.add(core.orders.resources.OrdersResource.class);
        resources.add(core.permissions.resources.PermissionResource.class);
        resources.add(core.tables.resources.TableResource.class);
        resources.add(core.vip.resources.VipResource.class);
        resources.add(core.workingHours.resources.WorkingHoursResource.class);
    }
    
}
