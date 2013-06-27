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

    Advertisement findByTitle(String title);

    List<Advertisement> findAll();

    List<Advertisement> findRange(int[] range);

    List<Advertisement> findByLimit(Integer pageIndex, Integer amount);

    int count();

    public void setEm(EntityManager em);

    public Advertisement createAdvertisement(String title, String description, double price, User user);

    public Advertisement addBid(Advertisement ad, Bid bid);
    
    //Robert J
    public List<Advertisement> findByUserId(User user);
    public int deleteById(int id);
    public int deleteByUserId(User user);
}
