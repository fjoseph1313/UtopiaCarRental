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
    private String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

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
        if(this.isDuplicated(customer)== false) {
        customer.setUserName(customer.getEmailAddress()); //assign username as email address
        customer.setPassword(getEncryptedPassword(customer.getPassword()));
        Customer addedCustomer = this.customerEJB.createCustomer(customer); //do manipulation with addedCustomer
        this.successMessage = "Customer Added Sucessfully";
        this.customer = null;
        return "registerCustomer";
        } else {
            this.successMessage = "Customer already exists ... Try another email";
         return null;
        }
    }

    public Customer searchCustomerByUserName(String customer) {
        return this.customerEJB.findCustomer(customer);
    }

    public boolean isDuplicated(Customer p) {
        System.out.println("Person isduplicate ..." + p.getEmailAddress());
        Customer custFetched = this.customerEJB.getCustomerByEmail(p.getEmailAddress());
        if (custFetched == null) {
            return false;
        } else {
            return true;
        }
    }
}
