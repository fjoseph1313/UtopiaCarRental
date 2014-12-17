/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Car;
import edu.utopia.entities.Customer;
import edu.utopia.entities.Location;
import edu.utopia.entities.Rent;
import edu.utopia.model.AdminEJB;
import edu.utopia.model.CarEJB;
import edu.utopia.model.CustomerEJB;
import edu.utopia.model.LocationEJB;
import edu.utopia.model.RentalEJB;
import edu.utopia.model.SendTLSMailEJB;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
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
public class RentalBean implements Serializable {

    @EJB
    private LocationEJB locationEJB;

    @EJB
    private SendTLSMailEJB sendMailEJB;
    @EJB
    private RentalEJB rentalEJB;
    @EJB
    private CarEJB carEJB;
    @EJB
    private CustomerEJB customerEJB;
    @EJB
    private AdminEJB adminEJB;

    private String pLocale;
    private String dLocale;
    private Long pLocationId;
    private Long dLocationId;
    private Date pDate;
    private Date dDate;
    private Long catId;
    private List criteriaCarsList;
    private Long carId;
    private Car selectedCar;
    private Rent addedRent;
    private String fromDate;
    private String toDate;
    private Long duration;
    private int listSize;
    private List<Rent> requestedRents;
    private Rent rent;
    private Customer customer;
    private Location ploc;
    private Location dloc;
    private String successMessage;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getpLocationId() {
        return pLocationId;
    }

    public void setpLocationId(Long pLocationId) {
        this.pLocationId = pLocationId;
    }

    public Long getdLocationId() {
        return dLocationId;
    }

    public void setdLocationId(Long dLocationId) {
        this.dLocationId = dLocationId;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Location getPloc() {
        return ploc;
    }

    public void setPloc(Location ploc) {
        this.ploc = ploc;
    }

    public Location getDloc() {
        return dloc;
    }

    public void setDloc(Location dloc) {
        this.dloc = dloc;
    }
    
    
    

    //fixed customer for renting testing
    private Customer cust = new Customer(new Long(1), "Francis", "Joseph", "652145879", "sinza", "DAr", "TZ", "xx", "zz", "uu", "123");

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

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setAddedRent(Rent addedRent) {
        this.addedRent = addedRent;
    }

    public Rent getAddedRent() {
        return addedRent;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public Long getDuration() {
        return duration;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public String searchCar() {
        //search car using locations and category
        System.out.println("plocalation=" + pLocationId);
        criteriaCarsList = this.carEJB.findCarsForRental(pLocationId, catId);
        listSize = this.carEJB.findCarsForRental(pLocationId, catId).size();
        return "rentalCarList";
    }

    public String rentCar(Car car) {
        System.out.println(car.getCarModel() + " selected car......." + car.getStatus());
        selectedCar = car;
        //work with this car!
        Rent newRent = new Rent();
        ploc = this.locationEJB.findById(pLocationId);
        dloc = this.locationEJB.findById(dLocationId);
        newRent.setPickUpLocation(ploc);
        newRent.setPickUpDate(pDate);
        newRent.setDropOffLocation(dloc);
        newRent.setDropOffDate(dDate);
        newRent.setRentStatus("requested"); //on request the status of rent is request..
        newRent.setCustomer(this.customerEJB.findCustomer(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser())); //this mus be the logged in customer!
        selectedCar.setStatus("reserved");
        Car rentedCar = this.carEJB.updateCar(selectedCar);
        newRent.setCar(rentedCar);

        addedRent = this.rentalEJB.createRent(newRent);//persisting the rent in the database..
        duration = this.rentalEJB.dateDifference(dDate, pDate);
        fromDate = this.rentalEJB.dateParser(addedRent.getPickUpDate());
        toDate = this.rentalEJB.dateParser(addedRent.getDropOffDate());
        System.out.println("fromDate ... " + fromDate);
        Double amt = selectedCar.getPricePerHour() * 24;
        Long amount = duration * amt.longValue();
        String carname = selectedCar.getCarManufacturingYear() + " " + selectedCar.getCarModel();
        //send confirmation email to customer

//        this.sendMailEJB.applicationEmail("cesc.joseph@gmail.com", "Francis Joseph", carname, duration, amount);
        //empty session rent
        newRent = null;

        return "rentalConfirmation";
    }

    public List<Rent> getRequestedRents() {
        return this.rentalEJB.findRequestedRent();
    }

    public void approveRequestedRent(Rent rent) {
        String reservationCode = generateCode(rent.getId());
        rent.setReservationCode(reservationCode);
        rent.setRentStatus("accepted");
        rent.getCar().setStatus("rented");
        this.carEJB.updateCar(rent.getCar());
        String name = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        System.out.println("user name is " + name);
        rent.setAdmin(this.adminEJB.findAdmin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
        rent.getAdmin().setUserName(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        this.rentalEJB.updateRent(rent);
        this.sendMailEJB.sendRentEmail(reservationCode, rent, "Rent Acceptance Confirmation", "confirmed");
    }

    public String generateCode(Long rendId) {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        int CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
        int hhh = localCalendar.get(Calendar.HOUR);
        int xxx = localCalendar.get(Calendar.MINUTE);
        int year = localCalendar.get(Calendar.YEAR) - 2000;
        String reservationCode = Integer.toString(year)
                + Integer.toString(CurrentDayOfYear) + Long.toString(rendId);
//System.out.println(reservationCode);
        return reservationCode;
    }

    public void disapproveRequestedRent(Rent rent) {
        rent.setRentStatus("cancel");
        rent.getCar().setStatus("available");
        this.carEJB.updateCar(rent.getCar());
//       rent.setAdmin(null);
        this.rentalEJB.updateRent(rent);
        this.sendMailEJB.sendRentEmail(null, rent, "Rent Cancel Confirmation", "cancelled");
    }

    public String getRequestedRentInformation(Rent rent) {
        this.rent = rent;
        return "viewRentInformation?faces-redirect=true";
    }
}
