/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Rent;
import edu.utopia.facades.RentFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class RentalEJB {
    @EJB
    private RentFacade rentFacade;

    public @NotNull Rent createRent(@NotNull Rent rent)
    {
        this.rentFacade.create(rent);
        return rent;
    }
}
