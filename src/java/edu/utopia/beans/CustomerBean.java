/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Customer;
import static edu.utopia.entities.EncryptPassword.getEncryptedPassword;
import edu.utopia.model.CustomerEJB;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "CustomerBean")
@RequestScoped
public class CustomerBean {

    @EJB
    private CustomerEJB customerEJB;
    private Customer customer;

    public CustomerBean() {
        this.customer = new Customer();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String registerCustomer() {
        customer.setUserName(customer.getEmailAddress()); //assign username as email address
        customer.setPassword(getEncryptedPassword(customer.getPassword()));
        Customer addedCustomer = this.customerEJB.createCustomer(customer); //do manipulation with addedCustomer
        return "registerCustomer";
    }

    public Customer searchCustomerByUserName(String customer) {
        return this.customerEJB.findCustomer(customer);
    }
}
