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
public interface AdvertisementFacadeLocal {

    void create(Advertisement advertisement);

    void edit(Advertisement advertisement);

    void remove(Advertisement advertisement);

    Advertisement find(Object id);

            /**
     * Finds advertisements by title
     *
     * @return a list containing advertisements
     */
    Advertisement findByTitle(String title);

    List<Advertisement> findAll();

    List<Advertisement> findRange(int[] range);

         /**
     * Finds advertisements by limit
     *
     * @return a list containing advertisements
     */
    List<Advertisement> findByLimit(Integer pageIndex, Integer amount);
    
        /**
     * Finds advertisements by limit and user
     *
     * @return a list containing advertisements
     */
    List<Advertisement> findByLimitAndUser(Integer pageIndex, Integer amount, User user);

    int count();

    public void setEm(EntityManager em);

       /**
     * Creates an advertisement
     * @return advertisement
     */
    public Advertisement createAdvertisement(String title, String description, double price, User user);

       /**
     * Adds a bid to the advertisement
     * 
     * @return advertisement
     */
    public Advertisement addBid(Advertisement ad, Bid bid);

    //Robert J
                /**
     * Finds advertisements by user id
     *
     * @return a list containing advertisements
     */
    public List<Advertisement> findByUserId(User user);

                    /**
     * Deletes advertisements by id
     *
     * @return 1: successful / -1: unsuccessful
     */
    public int deleteById(Advertisement ad);

                    /**
     * Deletes advertisements by user id
     *
     * @return 1: successful / -1: unsuccessful
     */
    public int deleteByUserId(User user);

         /**
     * Removes bid from advertisement
     *
     * @return advertisement
     */
    public Advertisement removeBid(Advertisement ad, Bid bid);
}
