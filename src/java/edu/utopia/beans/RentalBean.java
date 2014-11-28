/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.facades.CarFacade;
import edu.utopia.facades.RentFacade;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "RentalBean")
@RequestScoped
public class RentalBean
{
    @EJB
    private CarFacade carFacade;
    @EJB
    private RentFacade rentFacade; //DAO
    private String pLocale;
    private String dLocale;
    private Calendar pDate;
    private Calendar dDate;
    private Long catId;
    private List criteriaCarsList;

    public String getpLocale() {
        return pLocale;
    }

    public void setpLocale(String pLocale) {
        this.pLocale = pLocale;
    }

    public String getdLocale() {
        return dLocale;
    }

    public void setdLocale(String dLocale) {
        this.dLocale = dLocale;
    }

    public Calendar getpDate() {
        return pDate;
    }

    public void setpDate(Calendar pDate) {
        this.pDate = pDate;
    }

    public Calendar getdDate() {
        return dDate;
    }

    public void setdDate(Calendar dDate) {
        this.dDate = dDate;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public List getCriteriaCarsList() {
        return criteriaCarsList;
    }
    
    public String searchCar()
    {
        //search car using locations and category
        criteriaCarsList = this.carFacade.findCarByLocationAndCategory(pLocale, catId);
        return "rentalCarList";
    }
    
}
