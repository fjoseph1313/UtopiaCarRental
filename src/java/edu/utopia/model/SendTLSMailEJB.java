/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Rent;
import java.util.Date;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
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
@Stateless
public class SendTLSMailEJB {

    @Asynchronous
    public void applicationEmail(String toEmail, String customerName, String car, Long rentDuration, Long amt) {
        final String username = "ea.rentalcar@gmail.com";
        final String password = "Eaproject!";
        StringBuilder messageBody = new StringBuilder();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject("Car Rental Application Request");

            messageBody.append("Dear ").append(customerName).append(",\n\n");
            messageBody.append("Thanks For Applying Rent in Utopia Car Rental Company\n");
            messageBody.append("Summary of your enquiry:\n");
            messageBody.append("Car Selected : ").append(car).append("\n");
            messageBody.append("Duration : ").append(rentDuration).append(" Days\n");
            messageBody.append("Total Amount To Pay : ").append(amt).append("$\n\n");
            messageBody.append("Thanks for chosing Utopia Car Rental Services\n\n\n");
            messageBody.append("Best Regards,\nUtopia Car Rental.\n\n\n");
            messageBody.append("This is an automatic email, Please do not reply to this email");

            message.setText(messageBody.toString());

            Transport.send(message);

            System.out.println("Email sent from utopia....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    //both rent accepted and rent rejected email will be sent by using this function.
    @Asynchronous
    public void sendRentEmail(String reservationCode, Rent rent, String emailSubject, String status) {
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
            messageBody.append("\t\tThank you for choosing the services of Utopia Car Rental. Your request for " + "renting the following car has ").append(status).append(".\n\n");
            messageBody.append("Car Category: ").append(rent.getCar().getCategory().getCategoryName()).append("\n");
            messageBody.append("Car Model: ").append(rent.getCar().getCarModel()).append("\n");
            messageBody.append("Pick Up Location: ").append(rent.getPickUpLocation()).append("\n");
            messageBody.append("Pick Up Date: ").append((Date) rent.getPickUpDate()).append("\n");
            messageBody.append("Drop Off Location: ").append(rent.getDropOffLocation()).append("\n");
            messageBody.append("Drop Off Date ").append((Date) rent.getDropOffDate()).append("\n\n");

            if (reservationCode != null) {
                messageBody.append("Please use the following reservation Code to make your payments. \n");
                messageBody.append(reservationCode).append("\n\n");
            }

            messageBody.append("This is automated email please don't reply it.\n");
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
