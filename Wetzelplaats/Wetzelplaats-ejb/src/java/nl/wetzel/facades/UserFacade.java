/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import com.internet.custom.BCrypt;
import java.util.List;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;

/**
 *
 * @author Timo
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    public final static String FIND_BY_EMAIL = "User.findByEmail";
    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;

    public UserFacade() {
        super(User.class);
    }

    public User login(String email, String password) {
        User u = findByEmail(email);

        if (u != null) {
            if (!BCrypt.checkpw(password, u.getPassword())) {
                u = null;
            }
        }

        return u;
    }

    public User Register(String firstname, String lastname, String email, String password) {
        //check if data complete
        if (firstname == null || lastname == null || email == null || password == null) {
            return null;
        }

        //check duplicate
        User duplicate = findByEmail(email);

        if (duplicate != null) {
            throw new DuplicateEntityException("User already exists");
        } else {
            User u = new User();
            u.setFirstname(firstname);
            u.setLastname(lastname);
            u.setEmail(email);
            u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

            create(u);

            return u;
        }
    }

     @Override
    public User findByEmail(String email) {
        try {
            Query q = em.createNamedQuery(FIND_BY_EMAIL, User.class);
            q.setParameter("email", email);
            User u = (User) q.getSingleResult();

            return u;
        } catch (RuntimeException e) {
            //we now it can be several exceptions, but because we know it failed, return null            
            return null;

            //what you could do however, is log the exception that is not common
        }
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

   
    //</editor-fold>
}
