/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utopia.model;

import edu.utopia.entities.Car;
import edu.utopia.facades.CarFacade;
import edu.utopia.facades.RentFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author fjoseph1313
 */
@Stateless
public class RentalEJB {
    @EJB
    private RentFacade rentFacade;

    
}
