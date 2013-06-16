/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.helpers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.facades.BidFacadeLocal;

/**
 *
 * @author timo
 */
@Stateless
@LocalBean
public class BidHelper {
    @EJB
    private AdvertisementHelper advertisementHelper;

    @EJB
    private BidFacadeLocal bidFacade;        

    //<editor-fold defaultstate="collapsed" desc="comment">    
    public BidHelper() {
    }

    public BidHelper(BidFacadeLocal bidFacade) {
        this.bidFacade = bidFacade;
    }
    //</editor-fold>

    /**
     * Create a bid
     * @param price
     * @param user
     * @param ad
     * @return the bid that was created
     */
    public Bid createBid(double price, User user, Advertisement ad) {
        //TODO: Create unit tests for both bid and advertiser
        if (user == null || ad == null) {
            return null;
        }        

        Bid bid = new Bid();
        bid.setPrice(price);
        bid.setAdvertisementId(ad);
        bid.setUserId(user);
        
        bidFacade.create(bid);
        
        advertisementHelper.addBid(ad, bid);
        
        return bid;
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters>    
    public void setBidFacade(BidFacadeLocal bidFacade) {
        this.bidFacade = bidFacade;
    }
    
    public BidFacadeLocal getBidFacade() {
        return bidFacade;
    }
    //</editor-fold>
}
