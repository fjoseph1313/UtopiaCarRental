/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Rent;
import edu.utopia.facades.CarFacade;
import edu.utopia.facades.RentFacade;
import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author fjoseph1313
 */
@Named(value = "RentalBean")
@SessionScoped
public class RentalBean implements Serializable {

    @EJB
    private CarFacade carFacade;

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
    @EJB
    private RentFacade rentFacade; //DAO
    private String pLocale;
    private String dLocale;
    private Calendar pDate;
    private Calendar dDate;
    private Long carId;
    private Long customerId;
    private List criteriaCarsList;
    private List<Rent> requestedRentList;
    private Rent rent;

    public List<Rent> getRequestedRentList() {
        return requestedRentList;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setRequestedRentList(List<Rent> requestedRentList) {
        this.requestedRentList = requestedRentList;
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

    public Long getCarId() {
        return carId;
    }

    public void setCatId(Long carId) {
        this.carId = carId;
    }

    public List getCriteriaCarsList() {
        return criteriaCarsList;
    }

    public String searchCar() {
        //search car using locations and category
        criteriaCarsList = this.carFacade.findCarByLocationAndCategory(pLocale, carId);
        return "rentalCarList";
    }

    // return list of all requested car rentss
    public List<Rent> getRequestedRents() {
        System.out.println("requested rents-----");
        requestedRentList = this.rentFacade.findRequestedRent();
        return requestedRentList;
    }

    public void approveRequestedRent(Rent rent) {
        String reservationCode = UUID.randomUUID().toString();
        rent.setReservationCode(reservationCode);
        rent.setRentStatus("accepted");
        rent.getCar().setStatus("rented");
        carFacade.updateCar(rent.getCar());
//        rent.setAdmin(null);
        this.rentFacade.approveRent(rent);
        sendEmail(reservationCode, rent, "Rent Acceptance Confirmation", "confirmed");
    }

    public void disapproveRequestedRent(Rent rent) {
        rent.setRentStatus("available");
        rent.getCar().setStatus("cancel");
        carFacade.updateCar(rent.getCar());
//       rent.setAdmin(null);
        this.rentFacade.disapproveRent(rent);
        sendEmail(null, rent, "Rent Cancel Confirmation", "cancelled");
    }

    public String getRequestedRentInformation(Rent rent) {
        this.rent = rent;
        return "viewRentInformation?faces-redirect=true";
    }

    public void sendEmail(String reservationCode, Rent rent, String emailSubject, String status) {
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

    }

}
