/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Admin;
import edu.utopia.facades.AdminFacade;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "AdminBean")
@RequestScoped
public class AdminBean
{
    @EJB
    private AdminFacade adminFacade;
    private Admin admin;
    
    public AdminBean()
    {
        this.admin = new Admin();
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    public String registerAdmin()
    {
        //admin.setUserName(admin.getEmailAddress()); //assign username as email address but optional
        if(admin.getUserName().equals(""))
        {
            admin.setUserName(admin.getEmailAddress());
        }
        this.adminFacade.create(admin);
        return "registerEmployee";
    }
}
