/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.facades;

import edu.utopia.entities.Car;
import static edu.utopia.entities.Car.FIND_ALL;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    public List findCarByLocationAndCategory(Long locale, Long id) {
        System.out.println("locale in facade : "+ locale);
        Query query = em.createQuery("FROM Car r WHERE r.location.id=:loc AND r.category.id=:id AND r.status = 'available'");
        query.setParameter("loc", locale);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Car updateCar(Car car) {
        edit(car);
        return car;
    }

    //this method uses entity's named query to find all

    public List<Car> findCarsByNamedQuery() {
        TypedQuery query = em.createNamedQuery(FIND_ALL, Car.class);
        return query.getResultList();
    }

}
