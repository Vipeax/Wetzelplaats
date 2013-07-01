/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

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
    //Robert J
    public final static String FIND_BY_USER_ID = "Bid.findByUserId";
    public final static String DELETE_BY_ID = "Bid.deleteById";
    public final static String DELETE_BY_USER_ID = "Bid.deleteByUserId";
    public final static String DELETE_BY_ADVERTISEMENT_ID = "Bid.deleteByAdvertisementId";
    //R. Wetzels
    public static final String FIND_BY_LIMIT_AND_USER_ID ="Bid.findByLimitAndUser";
    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;
    @EJB
    private AdvertisementFacadeLocal advertisementFacade;

    /**
     * this setter is actually for unit testing
     *
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

    //Robert J
    @Override
    public List<Bid> findByAdvertisementId(int advertisementId) {
        try {
            Query q = em.createNamedQuery(BidFacade.FIND_BY_ADVERTISEMENT_ID, Bid.class);
            Advertisement ad = advertisementFacade.find(advertisementId);
            q.setParameter("advertisementId", ad);
            return q.getResultList();
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public List<Bid> findByUserId(User user) {
        List<Bid> result;

        Query q = em.createNamedQuery(BidFacade.FIND_BY_USER_ID, Bid.class);
        q.setParameter("userId", user);
        result = q.getResultList();

        return result;
    }

    @Override
    public int deleteById(int id) {
        try {
            Query q = em.createNamedQuery(BidFacade.DELETE_BY_ID, Bid.class);
            q.setParameter("bidId", id);
            q.executeUpdate();
            return 1;
        } catch (RuntimeException e) {
            return -1;
        }
    }

    @Override
    public int deleteByAdId(Advertisement ad) {
        try {
            Query q = em.createNamedQuery(BidFacade.DELETE_BY_ADVERTISEMENT_ID, Bid.class);
            q.setParameter("advertisementId", ad);
            q.executeUpdate();
            return 1;
        } catch (RuntimeException e) {
            return -1;
        }
    }

    @Override
    public int deleteByUserId(User user) {
        try {
            Query q = em.createNamedQuery(BidFacade.DELETE_BY_USER_ID, Bid.class);
            q.setParameter("userId", user);
            q.executeUpdate();
            return 1;
        } catch (RuntimeException e) {
            return -1;
        }
    }

    @Override
    public List<Bid> findByLimitAndUser(Integer pageIndex, Integer amount, User user) 
    {
        List<Bid> result;
        
        Query q = em.createNamedQuery(BidFacade.FIND_BY_LIMIT_AND_USER_ID, Bid.class);
        q.setMaxResults(amount);
        q.setFirstResult(pageIndex * amount);
        q.setParameter("userId", user);
        result = q.getResultList();

        return result;
    }
}
