/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Customer;
import edu.utopia.facades.CustomerFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class CustomerEJB {
    @EJB
    private CustomerFacade customerFacade;
    
    public @NotNull Customer createCustomer(@NotNull Customer customer)
    {
        this.customerFacade.create(customer);
        return customer;
    }

}
