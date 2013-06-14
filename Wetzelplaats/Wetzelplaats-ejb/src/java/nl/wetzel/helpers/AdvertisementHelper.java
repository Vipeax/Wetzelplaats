/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.helpers;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;
import nl.wetzel.facades.AdvertisementFacade;
import nl.wetzel.facades.AdvertisementFacadeLocal;

/**
 *
 * @author Timo
 */
@Stateless
@LocalBean
public class AdvertisementHelper {
    
    @EJB
    private AdvertisementFacadeLocal adLocal;    

    //<editor-fold defaultstate="collapsed" desc="constructors">
    public AdvertisementHelper() {
    }
    
    public AdvertisementHelper(AdvertisementFacade adLocal) {
        this.adLocal = adLocal;
    }
    //</editor-fold>

    public Advertisement createAdvertisement(String title, String description, Long price, User user) {
        if (title == null || description == null || price == null) {
            return null;
        }

        //check for duplicate title
//        try {
            Advertisement duplicate = getAdLocal().findByTitle(title);
            
            if (duplicate != null) {
                throw new DuplicateEntityException("Advertisement already exists");
            } else {
                //TODO define ePriceType
                Advertisement a = new Advertisement(0, title, description, price, false, 1);
                a.setUserId(user);
                a.setBidCollection(new ArrayList<Bid>());
                                
                getAdLocal().create(a);
                return a;
            }
//        } catch (RuntimeException e) {
//            
//            
//            //we know it can be several exceptions, but because we know it failed, return null            
//            return null;
//
//            //what you could do however, is log the exception that is not common
//        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/setters">
    public void setAdLocal(AdvertisementFacadeLocal adLocal) {
        this.adLocal = adLocal;
    }
    
    public AdvertisementFacadeLocal getAdLocal() {
        if (this.adLocal == null) {
            this.adLocal = new AdvertisementFacade();
        }
        
        return this.adLocal;
    }
    //</editor-fold>
}
