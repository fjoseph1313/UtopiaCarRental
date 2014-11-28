/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Customer;
import edu.utopia.facades.CustomerFacade;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "CustomerBean")
@RequestScoped
public class CustomerBean
{
    @EJB
    private CustomerFacade customerFacade;
    private Customer customer;
    public CustomerBean()
    {
        this.customer = new Customer();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String registerCustomer()
    {
        customer.setUserName(customer.getEmailAddress()); //assign username as email address
        this.customerFacade.create(customer);
        return "registerCustomer";
    }
}
