/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Car;
import static edu.utopia.entities.Car.FIND_ALL;
import edu.utopia.facades.CarFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class CarEJB
{
    @EJB
    private CarFacade carFacade;
    
    
    public @NotNull Car createCar(@NotNull Car car)
    {
        this.carFacade.create(car);
        return car;
    }
    //this method uses facade's find all method
    public List<Car> findAllCars()
    {
        return this.carFacade.findAll();
    }
    //this method uses facade's named query
    public List<Car> findCars()
    {
        return this.carFacade.findCarsByNamedQuery();
    }
    
    public List<Car> findCarsForRental(String loc, Long id)
    {
        return this.carFacade.findCarByLocationAndCategory(loc, id);
    }

}
