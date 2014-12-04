/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Car;
import edu.utopia.entities.Customer;
import edu.utopia.entities.Rent;
import edu.utopia.model.CarEJB;
import edu.utopia.model.RentalEJB;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "RentalBean")
@SessionScoped
public class RentalBean implements Serializable
{
    @EJB
    private RentalEJB rentalEJB;
    @EJB
    private CarEJB carEJB;
    
    private String pLocale;
    private String dLocale;
    private Date pDate;
    private Date dDate;
    private Long catId;
    private List criteriaCarsList;
    private Long carId;
    private Car selectedCar; // should work, but not working.. dont know why!!
    
    //fixed customer for renting testing
    private Customer cust = new Customer(new Long(1), "Francis", "Joseph", "652145879", "sinza", "DAr", "TZ", "xx", "zz", "uu");
    
    
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

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

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
    
    
    
    public String searchCar()
    {
        //search car using locations and category
        criteriaCarsList = this.carEJB.findCarsForRental(pLocale, catId);
        return "rentalCarList";
    }
   
    public String rentCar(Car car)
    {
        System.out.println(car.getCarModel()+ " selected car......."+car.getStatus());
        selectedCar = car;
        //work with this car!
        Rent newRent = new Rent();
        newRent.setPickUpLocation(pLocale);
        newRent.setPickUpDate(pDate);
        newRent.setDropOffLocation(dLocale);
        newRent.setDropOffDate(dDate);
        newRent.setRentStatus("request"); //on request the status of rent is request..
        //newRent.setCustomer(cust); //this mus be the logged in customer!
        selectedCar.setStatus("reserved");
        Car rentedCar = this.carEJB.updateCar(car);
        newRent.setCar(rentedCar);
        
        Rent addedRent = this.rentalEJB.createRent(newRent);//persisting the rent in the database..
        
        return "rentalConfirmation";
    }
}
