/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.wetzel.entities.Advertisement;

/**
 *
 * @author Timo
 */
@Stateless
public class AdvertisementFacade extends AbstractFacade<Advertisement> implements AdvertisementFacadeLocal {

    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvertisementFacade() {
        super(Advertisement.class);
    }

    @Override
    public List<Advertisement> findByLimit(Integer pageIndex, Integer amount) {
        List<Advertisement> result;

        Query q = em.createNamedQuery("Advertisement.findAll", Advertisement.class);
        q.setMaxResults(amount);
        q.setFirstResult(pageIndex * amount);

        result = q.getResultList();
        
        //make sure the highest bid is on top
        for (Advertisement advertisement : result) {
            Collections.sort((List)advertisement.getBidCollection(), new nl.wetzel.custom.CustomBidComparer());
        }

        return result;
    }

    @Override
    public Advertisement findByTitle(String title) {
        try {
            Query q = em.createNamedQuery("Advertisement.findByName");
            q.setParameter("name", title);

            return (Advertisement) q.getSingleResult();
        } catch (RuntimeException e) {
            //we know it can be several exceptions, but because we know it failed, return null            
            return null;

            //what you could do however, is log the exception that is not common
        }
    }
}
