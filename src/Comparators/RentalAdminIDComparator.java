/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparators;

import Project_LendMe.Rentals;
import java.util.Comparator;

/**
 * Comparator-Class used for sorting 
 * methods called in archive-table and rental-list-table 
 * 
 * @author Katharina
 */
public class RentalAdminIDComparator implements Comparator<Rentals>{
    @Override
    public int compare(Rentals r1, Rentals r2) {
        int c = r1.getAdministrators_AdminID() - r2.getAdministrators_AdminID();
        return c;
    }

    @Override
    public Comparator<Rentals> reversed() {
        return Comparator.super.reversed(); 
    }
    
    
}
