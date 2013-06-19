/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;

/**
 *
 * @author Timo
 */
@Stateless
public class BidFacade extends AbstractFacade<Bid> implements BidFacadeLocal {

    public final static String FIND_BY_ADVERTISEMENT_ID = "Bid.findByAdvertisementId";
    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;
    @EJB
    private AdvertisementFacadeLocal advertisementFacade;

    /**
     * this setter is actually for unit testing
     * @param advertisementFacade
     */
    @Override
    public void setAdvertisementFacade(AdvertisementFacadeLocal advertisementFacade) {
        this.advertisementFacade = advertisementFacade;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public BidFacade() {
        super(Bid.class);
    }

    @Override
    public List<Bid> findByAdvertisementId(int advertisementId) {

        Query q = em.createNamedQuery(BidFacade.FIND_BY_ADVERTISEMENT_ID, Bid.class);
        q.setParameter("advertisementId", advertisementId);

        return q.getResultList();
    }

    /**
     * Create a bid
     *
     * @param price
     * @param user
     * @param ad
     * @return the bid that was created
     */
    @Override
    public Bid createBid(double price, User user, Advertisement ad) {
        //TODO: Create unit tests for both bid and advertiser
        if (user == null || ad == null) {
            return null;
        }

        Bid bid = new Bid();
        bid.setPrice(price);
        bid.setAdvertisementId(ad);
        bid.setUserId(user);

        create(bid);
        advertisementFacade.addBid(ad, bid);

        return bid;
    }
}
