/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.facades;

import edu.utopia.entities.Car;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class CarFacade extends AbstractFacade<Car> {

    @PersistenceContext(unitName = "UtopiaCarRentalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarFacade() {
        super(Car.class);
    }

    public List findCarByLocationAndCategory(String locale, Long id) {
        Query query = em.createQuery("FROM Car r WHERE r.location=:loc AND r.category.id=:id");
        query.setParameter("loc", locale);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Car updateCar(Car car) {
        edit(car);
        return car;
    }
}
