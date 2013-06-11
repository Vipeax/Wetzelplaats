/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

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
        q.setFirstResult(pageIndex);

        result = q.getResultList();

        return result;
    }
}
