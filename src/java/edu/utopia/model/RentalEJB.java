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

    public @NotNull
    Rent createRent(@NotNull Rent rent) {
        this.rentFacade.create(rent);
        return rent;
    }

    public Long dateDifference(Date date1, Date date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Long diff = date1.getTime() - date2.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        
        return diffDays;
    }
    public String dateParser(Date dt)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = sdf.format(dt);
        return formatedDate;
    }
}
