/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;

/**
 *
 * @author Timo
 */
@Stateless
public class AdvertisementFacade extends AbstractFacade<Advertisement> implements AdvertisementFacadeLocal {

    public static final String FIND_ALL = "Advertisement.findAll";
    public static final String FIND_BY_NAME = "Advertisement.findByName";
    //Robert J
    public static final String FIND_BY_USER_ID = "Advertisement.findByUserId";
    public static final String DELETE_BY_ID = "Advertisement.deleteById";
    public static final String DELETE_BY_USER_ID = "Advertisement.deleteByUserId";
    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public AdvertisementFacade() {
        super(Advertisement.class);
    }

    @Override
    public List<Advertisement> findByLimit(Integer pageIndex, Integer amount) {
        List<Advertisement> result;

        Query q = em.createNamedQuery(AdvertisementFacade.FIND_ALL, Advertisement.class);
        q.setMaxResults(amount);
        q.setFirstResult(pageIndex * amount);

        result = q.getResultList();

        return result;
    }

    @Override
    public Advertisement findByTitle(String title) {
        try {
            Query q = em.createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class);
            q.setParameter("name", title);

            return (Advertisement) q.getSingleResult();
        } catch (RuntimeException e) {
            //we know it can be several exceptions, but because we know it failed, return null            
            return null;

            //what you could do however, is log the exception that is not common
        }
    }

    @Override
    public Advertisement createAdvertisement(String title, String description, double price, User user) {
        if (title == null || description == null) {
            return null;
        }
        //check for duplicate title
        Advertisement duplicate = findByTitle(title);

        if (duplicate != null) {
            throw new DuplicateEntityException("Advertisement already exists");
        } else {
            //TODO define ePriceType
            Advertisement a = new Advertisement(0, title, description, price, false, 1);
            a.setUserId(user);
            a.setBidCollection(new ArrayList<Bid>());

            create(a);
            return a;
        }
    }

    @Override
    public Advertisement addBid(Advertisement ad, Bid bid) {
        List<Bid> bidCollection = (List) ad.getBidCollection();

        if (bidCollection == null) {
            bidCollection = new LinkedList<Bid>();
        }

        bidCollection.add(0, bid);
        ad.setBidCollection(bidCollection);

        edit(ad);
        return ad;
    }

    //Robert J
    @Override
    public Advertisement removeBid(Advertisement ad, Bid bid) {
        List<Bid> bidCollection = (List) ad.getBidCollection();

        if (bidCollection == null) {
            bidCollection = new LinkedList<Bid>();
        }

        bidCollection.remove(bid);
        ad.setBidCollection(bidCollection);

        edit(ad);
        return ad;
    }

    @Override
    public List<Advertisement> findByUserId(User user) {
        List<Advertisement> result;

        Query q = em.createNamedQuery(AdvertisementFacade.FIND_BY_USER_ID, Advertisement.class);
        q.setParameter("userId", user);
        result = q.getResultList();

        return result;
    }

    @Override
    public int deleteById(int id) {
        try {
            Query q = em.createNamedQuery(AdvertisementFacade.DELETE_BY_ID, Advertisement.class);
            q.setParameter("adId", id);
            q.executeUpdate();
            return 1;
        } catch (RuntimeException e) {
            return -1;
        }
    }

    @Override
    public int deleteByUserId(User user) {
        try {
            Query q = em.createNamedQuery(AdvertisementFacade.DELETE_BY_USER_ID, Advertisement.class);
            q.setParameter("userId", user);
            q.executeUpdate();
            return 1;
        } catch (RuntimeException e) {
            return -1;
        }
    }
}
