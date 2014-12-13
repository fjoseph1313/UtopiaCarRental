/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author DiRauniyar
 */
@Entity
public class Customer extends Person {

    @OneToMany(mappedBy = "customer", targetEntity = Rent.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rent> custRentList;

    public Customer() {
    }

    public Customer(Long id, String f, String l, String p, String st, String c, String sta, String z, String e, String u, String pass) {
        super(id, f, l, p, st, c, sta, z, e, u, pass);
    }

    public List<Rent> getCustRentList() {
        return custRentList;
    }

    public void setCustRentList(List<Rent> custRentList) {
        this.custRentList = custRentList;
    }

}
