/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author fjoseph1313
 */
@javax.ws.rs.ApplicationPath("webresources")
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
        resources.add(edu.utopia.service.AdminFacadeREST.class);
        resources.add(edu.utopia.service.CarFacadeREST.class);
        resources.add(edu.utopia.service.CategoryFacadeREST.class);
        resources.add(edu.utopia.service.CustomerFacadeREST.class);
        resources.add(edu.utopia.service.LocationFacadeREST.class);
        resources.add(edu.utopia.service.PaymentFacadeREST.class);
        resources.add(edu.utopia.service.PersonFacadeREST.class);
        resources.add(edu.utopia.service.RentFacadeREST.class);
    }
    
}
