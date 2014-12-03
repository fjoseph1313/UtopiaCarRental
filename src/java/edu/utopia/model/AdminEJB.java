/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Admin;
import edu.utopia.facades.AdminFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class AdminEJB {
    @EJB
    private AdminFacade adminFacade;
    
    public @NotNull Admin createAdmin(@NotNull Admin admin)
    {
        this.adminFacade.create(admin);
        return admin;
    }

}
