/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Location;
import edu.utopia.facades.LocationFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author dipika
 */
@Stateless
public class LocationEJB {
    @EJB
    private LocationFacade locationFacade;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Location findById(Long id) {
       return this.locationFacade.find(id);
    } 
}
