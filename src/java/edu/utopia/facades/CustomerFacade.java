/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.facades;

import edu.utopia.entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "UtopiaCarRentalPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    public Customer findCustomerByUserName(String customer) {
        Query query = em.createQuery("From Person r WHERE r.userName=:customer");
        query.setParameter("customer", customer);
        return (Customer) query.getSingleResult();
    }

    public Customer getCustByEmail(String email)//the should work fxn
    {
        System.out.println("how about here??? Custfacade....." + email);
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.emailAddress = :mail");
        query.setParameter("mail", email);
        if (query.getResultList().size() > 0) {
            System.out.println("size of the list is " + query.getResultList().size());
            Customer cust = (Customer) query.getSingleResult();
            System.out.println("Customer found: " + cust.getLastName());
            return cust;
        } else {
            return null;
        }
    }
  


}
