/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import com.internet.custom.BCrypt;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.wetzel.entities.User;

/**
 *
 * @author Timo
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }  

    @Override
    public User getUser(String email) {
        Query q = em.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        User u = (User) q.getSingleResult();

        return u;
    }
}
