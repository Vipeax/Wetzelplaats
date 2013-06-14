/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.List;
import javax.ejb.Local;
import nl.wetzel.entities.Bid;

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

    int count();
}
