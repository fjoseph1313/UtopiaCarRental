/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Komal
 */
@Named(value = "logOutBean")
@Dependent
public class LogOutBean implements Serializable {

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    private static Logger log = Logger.getLogger(LogOutBean.class.getName());

    public String logout() {
        System.out.println("m here");
        String result = "/faces/index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("m here 1");
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        System.out.println("m here 2");
        try {
            System.out.println("In try Block");
            request.logout();
        } catch (ServletException e) {
            System.out.println("in exception");
            log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "/faces/loginError?faces-redirect=true";
        }

        return result;
    }
}
