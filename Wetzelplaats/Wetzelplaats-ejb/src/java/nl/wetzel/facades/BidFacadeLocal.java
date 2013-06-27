/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;

/**
 *
 * @author Timo
 */
@Local
public interface BidFacadeLocal {

    void create(Bid bid);

    void edit(Bid bid);

    void remove(Bid bid);

    Bid find(Object id);

    List<Bid> findAll();

    List<Bid> findRange(int[] range);
    
    List<Bid> findByAdvertisementId(int advertisementId);

    int count();

    public Bid createBid(double price, User user, Advertisement ad);

    public void setEm(EntityManager em);

    public void setAdvertisementFacade(AdvertisementFacadeLocal advertisementFacade);
    
    //Robert J
    public List<Bid> findByUserId(User user);
    
    public int deleteById(Advertisement ad);
    
    public int deleteByUserId(User user);
}
