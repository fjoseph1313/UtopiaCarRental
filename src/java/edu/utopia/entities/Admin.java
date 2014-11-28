/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author fjoseph1313
 */
@Entity
public class Admin extends Person 
{
    @OneToMany(mappedBy="admin", targetEntity=Rent.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Rent> adminRentList;
    
    public Admin(){}
    public Admin(Long id, String f, String l, String p, String st, String c, String sta, String z, String e, String u) 
    {
        super(id, f, l, p, st, c, sta, z, e, u);
    }

    public List<Rent> getAdminRentList() {
        return adminRentList;
    }

    public void setAdminRentList(List<Rent> adminRentList) {
        this.adminRentList = adminRentList;
    }
    
    
}
