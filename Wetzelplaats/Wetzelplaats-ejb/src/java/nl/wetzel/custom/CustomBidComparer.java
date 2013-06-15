/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.custom;

import java.util.Comparator;
import nl.wetzel.entities.Advertisement;

/**
 *
 * @author timo
 */
public class CustomBidComparer implements Comparator<Advertisement> {

    @Override
    public int compare(Advertisement o1, Advertisement o2) {
        double price1 = o1.getPrice();
        double price2 = o2.getPrice();
        
        if(price1 > price2) return (int)price1;
        else return (int)price2;
    }
    
}
