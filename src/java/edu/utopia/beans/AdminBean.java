/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Admin;
import static edu.utopia.entities.EncryptPassword.getEncryptedPassword;
import edu.utopia.model.AdminEJB;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "AdminBean")
@RequestScoped
public class AdminBean {

    @EJB
    private AdminEJB adminEJB;
    private Admin admin;

    public AdminBean() {
        this.admin = new Admin();
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String registerAdmin() {
        //admin.setUserName(admin.getEmailAddress()); //assign username as email address but optional
        if (admin.getUserName().equals("")) {
            admin.setUserName(admin.getEmailAddress());
        }
        admin.setPassword(getEncryptedPassword(admin.getPassword()));
        Admin addedAdmin = this.adminEJB.createAdmin(admin);
        return "registerEmployee";
    }

    public Admin searchAdminByUserName(String admin) {
        return this.adminEJB.findAdmin(admin);
    }
}
