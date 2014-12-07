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
import edu.utopia.model.SendTLSMailEJB;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "RentalBean")
@SessionScoped
public class RentalBean implements Serializable {

    @EJB
    private SendTLSMailEJB sendMailEJB;
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
    private Car selectedCar;
    private Rent addedRent;
    private String fromDate;
    private String toDate;
    private Long duration;
    private int listSize;
    private List<Rent> requestedRents;
    private Rent rent;

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
        criteriaCarsList = this.carEJB.findCarsForRental(pLocale, catId);
        listSize = this.carEJB.findCarsForRental(pLocale, catId).size();
        return "rentalCarList";
    }

    public String rentCar(Car car) {
        System.out.println(car.getCarModel() + " selected car......." + car.getStatus());
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

        addedRent = this.rentalEJB.createRent(newRent);//persisting the rent in the database..
        duration = this.rentalEJB.dateDifference(dDate, pDate);
        fromDate = this.rentalEJB.dateParser(addedRent.getPickUpDate());
        toDate = this.rentalEJB.dateParser(addedRent.getDropOffDate());
        System.out.println("fromDate ... " + fromDate);
        Double amt = selectedCar.getPricePerHour() * 24;
        Long amount = duration * amt.longValue();
        String carname = selectedCar.getCarManufacturingYear() + " " + selectedCar.getCarModel();
        //send confirmation email to customer

        //this.sendMailEJB.applicationEmail("cesc.joseph@gmail.com", "Francis Joseph", carname, duration, amount);
        //empty session rent
        newRent = null;

        return "rentalConfirmation";
    }

    public List<Rent> getRequestedRents() {
        System.out.println("requested rents-----");
        return this.rentalEJB.findRequestedRent();
    }

    public void approveRequestedRent(Rent rent) {
        String reservationCode = UUID.randomUUID().toString();
        rent.setReservationCode(reservationCode);
        rent.setRentStatus("accepted");
        rent.getCar().setStatus("rented");
        this.carEJB.updateCar(rent.getCar());
        //carFacade.updateCar(rent.getCar());
        //rent.setAdmin(null);
        this.rentalEJB.updateRent(rent);
        //sendEmail(reservationCode, rent, "Rent Acceptance Confirmation", "confirmed");
    }
    
    public void disapproveRequestedRent(Rent rent) {
        rent.setRentStatus("cancel");
        rent.getCar().setStatus("available");
        this.carEJB.updateCar(rent.getCar());
//       rent.setAdmin(null);
        this.rentalEJB.updateRent(rent);
        //sendEmail(null, rent, "Rent Cancel Confirmation", "cancelled");
    }
    
    public String getRequestedRentInformation(Rent rent) {
        this.rent = rent;
        return "viewRentInformation?faces-redirect=true";
    }
    
    //this has to be transfered to the SendTLSMail EJB ******Komal
    /*public void sendEmail(String reservationCode, Rent rent, String emailSubject, String status) {
        final String fromEmail = "ea.rentalcar@gmail.com"; //requires valid gmail id
        final String password = "Eaproject!"; // correct password for gmail id
        final String toEmail = rent.getCustomer().getEmailAddress(); // can be any email id 

        StringBuilder messageBody = new StringBuilder();

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject(emailSubject);

            messageBody.append("Dear ").append(rent.getCustomer().getFirstName()).append(" ").append(rent.getCustomer().getLastName()).append("," + "\n\n");
            messageBody.append("Thank you for choosing the services of Utopia Car Rental. Your request for " + "renting the following car has ").append(status).append(".\n\n");
            messageBody.append("Car Category: ").append(rent.getCar().getCategory().getCategoryName()).append("\n\n");
            messageBody.append("Car Model: ").append(rent.getCar().getCarModel()).append("\n\n");
            messageBody.append("Pick Up Location: ").append(rent.getPickUpLocation()).append("\n\n");
            messageBody.append("Pick Up Date: ").append((Date) rent.getPickUpDate()).append("\n\n");
            messageBody.append("Drop Off Location: ").append(rent.getDropOffLocation()).append("\n\n");
            messageBody.append("Drop Off Date ").append((Date) rent.getDropOffDate()).append("\n\n");

            if (reservationCode != null) {
                messageBody.append("Please use the following reservation Code to make your payments. \n\n");
                messageBody.append(reservationCode).append("\n\n");
            }

            messageBody.append("This is automated email please don't reply it.\n\n");
            messageBody.append("Best Regards\n");

            message.setText(messageBody.toString());
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            throw new RuntimeException(mex);
        }

    }*/
}
