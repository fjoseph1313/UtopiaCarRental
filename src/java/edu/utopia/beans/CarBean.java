/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Car;
import edu.utopia.entities.Category;
import edu.utopia.facades.CarFacade;
import edu.utopia.facades.CategoryFacade;
import edu.utopia.model.CarEJB;
import edu.utopia.model.CategoryEJB;
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
public class CarBean 
{
    @EJB
    private CarEJB carEJB;
    @EJB
    private CategoryEJB categoryEJB;
    
    
    private Car car;
    private Long catId;
    
    public CarBean()
    {
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
    
    
    public String registerCar()
    {
        //before persiting car entity, fetch its category from a selected category.
        Category cat = (Category)this.categoryEJB.findById(catId);
        car.setCategory(cat);
        car.setStatus("available");
        this.carEJB.createCar(car);//persisting a car entity and empty the form
        return "addCar";
    }
    
    public List<Car> getCarList()
    {
        //return this.carEJB.findAllCars(); //from ejb's facade
        return this.carEJB.findCars(); //from ejb's named query
    }
    
}
