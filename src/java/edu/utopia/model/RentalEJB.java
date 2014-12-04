/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Rent;
import edu.utopia.facades.RentFacade;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    public void dateDifference(Date date1, Date date2)
    {
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dt1 = format.parse(date1);
        Date dt2 = format.parse(date2);
        Days.daysBetween();*/
        
    }
}
