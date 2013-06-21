/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;
import nl.wetzel.entities.User;

/**
 *
 * @author Timo
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();

    public User findByEmail(String email);

    public void setEm(EntityManager em);

    public User Register(String firstname, String lastname, String email, String password);

    public User login(String email, String password);
}
