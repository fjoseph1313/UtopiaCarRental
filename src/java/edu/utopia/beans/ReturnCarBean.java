/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.beans;

import edu.utopia.entities.Car;
import edu.utopia.entities.Payment;
import edu.utopia.entities.Rent;
import edu.utopia.facades.PaymentFacade;
import edu.utopia.model.AdminEJB;
import edu.utopia.model.CarEJB;
import edu.utopia.model.RentalEJB;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author dipika
 */
@Named(value = "ReturnCarBean")
@SessionScoped
public class ReturnCarBean implements Serializable {
    @EJB
    private AdminEJB adminEJB;
    @EJB
    private PaymentFacade paymentFacade;

    @EJB
    private CarEJB carEJB;
    private Date actualDropOffDate;
    private String show;
    private String updatedCarCondition;
    private double charge;
    private double extraCharge;
    private double totalCharge;
    private int totalDays;
    private int size;
    private String reservationCode;
    private Rent rentDetail;
    private Payment payment;

    @EJB
    private RentalEJB rentalEJB;

    public double getCharge() {
        return charge;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public double getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(double extraCharge) {
        this.extraCharge = extraCharge;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public String getUpdatedCarCondition() {
        return updatedCarCondition;
    }

    public void setUpdatedCarCondition(String updatedCarCondition) {
        this.updatedCarCondition = updatedCarCondition;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public Date getActualDropOffDate() {
        return actualDropOffDate;
    }

    public void setActualDropOffDate(Date actualDropOffDate) {
        this.actualDropOffDate = actualDropOffDate;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    /**
     * Creates a new instance of ReturnCarBean
     */
    public ReturnCarBean() {
        this.payment = new Payment();
    }

    public Rent getRentDetail() {
        return rentDetail;
    }

    public void setRentDetail(Rent rentDetail) {
        this.rentDetail = rentDetail;
    }

    public String searchReservation() {
        rentDetail = this.rentalEJB.findRentByReservationCode(reservationCode);
        if (rentDetail == null) {
            this.size = 0;
        } else {
            this.size = 1;
        }
        return "reservationDetail";
    }

    public String updateReturnCar() {
        System.out.println("Car Condition" + this.updatedCarCondition);
        System.out.println("Actual Drop off Date :" + this.actualDropOffDate);

        Car thisCar = rentDetail.getCar();
        thisCar.setCarCondition(this.updatedCarCondition);
        //change the status of the car to available 
        thisCar.setStatus("Available");
        this.carEJB.updateCar(thisCar);
        if (this.actualDropOffDate == null) {
            System.out.println("Actual return date" + this.actualDropOffDate);
            this.actualDropOffDate = this.rentDetail.getDropOffDate();
        }
        Date expectedReturnDate = this.rentDetail.getDropOffDate();

        //getting date difference
        long diff = this.actualDropOffDate.getTime() - expectedReturnDate.getTime();
        this.extraCharge = diff / (24 * 60 * 60 * 1000) * rentDetail.getCar().getPricePerHour();

        //initially booked days
        long d = this.rentDetail.getDropOffDate().getTime() - rentDetail.getPickUpDate().getTime();
        int bookedDays = (int) d / (24 * 60 * 60 * 1000);

        //get total number of days
        long diff1 = this.actualDropOffDate.getTime() - rentDetail.getPickUpDate().getTime();
        totalDays = (int) diff1 / (24 * 60 * 60 * 1000);
        this.charge = bookedDays * (rentDetail.getCar().getPricePerHour());
        this.totalCharge = this.extraCharge + this.charge;

        this.rentDetail.setDropOffDate(actualDropOffDate);
        this.rentalEJB.updateRent(rentDetail);
        //update the return date

        return "reciept";

    }

    public String makePayment() {
        System.out.println("total charge" + this.totalCharge);
        this.payment.setAmount(this.totalCharge);
        Date date = new Date();
        this.payment.setPaymentDate(date);
        if (this.extraCharge != 0) {
            this.payment.setPaymentType("extra");
        } else {
            this.payment.setPaymentType("normal");
        }
        this.payment.setRent(this.rentDetail);
//        newRent.setCustomer(this.customerEJB.findCustomer(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser())); //this mus be the logged in customer!
        this.payment.setAdmin(this.adminEJB.findAdmin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
        this.paymentFacade.create(payment);
        return "paymentReceived";
    }

}
