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

        if(bidCollection == null) {
            bidCollection = new LinkedList<Bid>();
        }
        
        bidCollection.add(0, bid);
        ad.setBidCollection(bidCollection);

        edit(ad);
        return ad;
    }
}
