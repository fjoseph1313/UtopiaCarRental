/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import java.util.Properties;
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

    public void applicationEmail(String toEmail, String customerName, String car, Long rentDuration, Long amt)
    {
        final String username = "sharemum@gmail.com";
        final String password = "sharemum@gmail";
        StringBuilder messageBody  = new StringBuilder();

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
            message.setFrom(new InternetAddress("admin@utopiacarrental.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject("Car Rental Application Request");
            
            messageBody.append("Dear "+customerName+",\n\n");
            messageBody.append("Thanks For Applying Rent in Utopia Car Rental Company\n");
            messageBody.append("Summary of your enquiry:\n");
            messageBody.append("Car Selected : "+car+"\n");
            messageBody.append("Duration : "+rentDuration+" Days\n");
            messageBody.append("Total Amount To Pay : "+amt+"$\n\n");
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
}
