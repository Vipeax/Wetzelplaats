/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.List;
import javax.ejb.Local;
import nl.wetzel.entities.Advertisement;

/**
 *
 * @author Timo
 */
@Local
public interface AdvertisementFacadeLocal {

    void create(Advertisement advertisement);

    void edit(Advertisement advertisement);

    void remove(Advertisement advertisement);

    Advertisement find(Object id);

    List<Advertisement> findAll();

    List<Advertisement> findRange(int[] range);

    int count();
    
}
