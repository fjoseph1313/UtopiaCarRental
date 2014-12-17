/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Admin;
import static edu.utopia.entities.EncryptPassword.getEncryptedPassword;
import edu.utopia.model.AdminEJB;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.EJB;

/**
 *
 * @author fjoseph1313
 */
@Startup
@Singleton
public class UtopiaInitializer {
    @EJB
    private AdminEJB adminEJB;
    private Admin admin;
    
    //private final MD5Util md5Util = MD5Util.getInstance();

    @PostConstruct
    public void initializer()
    {
        String pass = getEncryptedPassword("josepass");
        admin = new Admin(new Long(1), "Francis", "Joseph", "6412757219", "1000N 4th ST", 
                "Fairfield", "Iowa", "52557", "fjoseph@utopia.com", 
                "fjoseph@utopia.com", pass);
        
        this.adminEJB.createAdmin(admin);
    }
}
