/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparators;

import Project_LendMe.Rentals;
import java.util.Comparator;

/**
 *
 * @author Katharina
 */
public class returnDateComparatorDESC implements Comparator <Rentals> {

    @Override
    public int compare(Rentals r1, Rentals r2) {
        int c = r2.getReturnDate().compareTo(r1.getReturnDate());
        return c;
    }
    
}
