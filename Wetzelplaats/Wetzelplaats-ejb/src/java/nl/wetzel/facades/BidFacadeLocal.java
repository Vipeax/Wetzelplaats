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

                    /**
     * Finds bid by advertisement id
     *
     * @return a list containing bids
     */
    List<Bid> findByAdvertisementId(int advertisementId);

    int count();

    public Bid createBid(double price, User user, Advertisement ad);

    public void setEm(EntityManager em);

    public void setAdvertisementFacade(AdvertisementFacadeLocal advertisementFacade);

    //Robert J
         /**
     * Finds bids by limit and user
     *
     * @return a list containing bids
     */
    public List<Bid> findByUserId(User user);
    
         /**
     * Finds bids by limit and user
     *
     * @return a list containing bids
     */
    //R. Wetzels
    List<Bid> findByLimitAndUser(Integer pageIndex, Integer amount, User user);                        

                        /**
     * Deletes bid by id
     *
     * @return 1: successful / -1: unsuccessful
     */
    public int deleteById(Bid bid);

                            /**
     * Deletes bid by advertisement id
     *
     * @return 1: successful / -1: unsuccessful
     */
    public int deleteByAdId(Advertisement ad);

                            /**
     * Deletes bid by user id
     *
     * @return 1: successful / -1: unsuccessful
     */
    public int deleteByUserId(User user);
}
