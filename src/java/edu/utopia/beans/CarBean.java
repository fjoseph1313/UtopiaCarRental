/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Car;
import edu.utopia.entities.Category;
import edu.utopia.entities.Location;
import edu.utopia.facades.CarFacade;
import edu.utopia.model.CarEJB;
import edu.utopia.model.CategoryEJB;
import edu.utopia.model.LocationEJB;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "CarBean")
@RequestScoped
public class CarBean {
    @EJB
    private LocationEJB locationEJB;

    @EJB
    private CarFacade carFacade;
    @EJB
    private CarEJB carEJB;
    @EJB
    private CategoryEJB categoryEJB;
    private String successMessage;

    private Car car;
    private Long catId;
    private Long locationId;
    
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    
    public CarBean() {
        this.car = new Car();
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
    
    
    
    

    public String registerCar() {
        //before persiting car entity, fetch its category from a selected category.
        Category cat = (Category) this.categoryEJB.findById(catId);
        Location loc = (Location) this.locationEJB.findById(locationId);
        car.setLocation(loc);
        car.setCategory(cat);
        car.setStatus("available");
        this.carEJB.createCar(car);//persisting a car entity and empty the form
        this.successMessage = "Car added Successfully!";
        this.car = null;
        return "addCar";
    }
    

    public List<Car> getCarList() {
        //return this.carEJB.findAllCars(); //from ejb's facade
        return this.carEJB.findCars(); //from ejb's named query
    }

    public void updateCar(Car car) {
        this.car.setStatus(car.getStatus());
        this.carFacade.updateCar(car);
    }

}
