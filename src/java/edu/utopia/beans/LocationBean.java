/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Location;
import edu.utopia.facades.LocationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author dipika
 */
@Named(value = "LocationBean")
@RequestScoped
public class LocationBean {

    @EJB
    private LocationFacade locationFacade;
    private Location location;
//    public List<String> getLocation(String query) {
//         List<String> results = new ArrayList<String>();
//        for(int i = 0; i < 10; i++) {
//            results.add(query + i);
//        }
//         
//        return results;
//    }

    public List<Location> getLocationList() {
        return this.locationFacade.findAll();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocationBean() {
        this.location = new Location();
    }

    public String addLocation() {
        // location.setLocationName(location.getLocationName());
        this.locationFacade.create(location);

        return "addLocation";
    }

}
