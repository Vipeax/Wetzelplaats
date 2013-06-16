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
import nl.wetzel.entities.Bid;

/**
 *
 * @author Timo
 */
@Stateless
public class BidFacade extends AbstractFacade<Bid> implements BidFacadeLocal {

    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BidFacade() {
        super(Bid.class);
    }

    @Override
    public List<Bid> findByAdvertisementId(int advertisementId) {
        
        Query q = em.createNamedQuery("Bid.findByAdvertisementId");
        q.setParameter("advertisementId", advertisementId);
        
        return q.getResultList();
    }
}
