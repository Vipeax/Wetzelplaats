/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.facades;

import com.internet.custom.BCrypt;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.wetzel.entities.User;
import nl.wetzel.entities.User.UserType;
import nl.wetzel.exception.DuplicateEntityException;

/**
 *
 * @author Timo
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @EJB
    private BidFacadeLocal bidFacade;
    @EJB
    private AdvertisementFacadeLocal advertisementFacade;
    public final static String FIND_BY_EMAIL = "User.findByEmail";
    public final static String DELETE_BY_ID = "User.deleteById";
    //Robert J
    public static final String FIND_ALL = "User.findAll";
    @PersistenceContext(unitName = "Wetzelplaats-ejbPU")
    private EntityManager em;

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User login(String email, String password) {
        User u = findByEmail(email);

        if (u != null) {
            if (!BCrypt.checkpw(password, u.getPassword())) {
                u = null;
            }
        }

        return u;
    }

    @Override
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
            u.setUserType(UserType.User);

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

    //Robert J  
    @Override
    public List<User> findByLimit(Integer pageIndex, Integer amount) {
        List<User> result;

        Query q = em.createNamedQuery(UserFacade.FIND_ALL, User.class);
        q.setMaxResults(amount);
        q.setFirstResult(pageIndex * amount);

        result = q.getResultList();

        return result;
    }

    //Robert J
    @Override
    public int deleteById(int id) {
        try {
            User us = find(id);
            advertisementFacade.deleteByUserId(us);
            bidFacade.deleteByUserId(us);

            Query q2 = em.createNamedQuery(UserFacade.DELETE_BY_ID, User.class);
            q2.setParameter("userId", id);
            q2.executeUpdate();
            return 1;


        } catch (RuntimeException e) {
            return -1;
        }
    }
    
    @Override
    public String hashpwd(String pwd) 
    {
        return BCrypt.hashpw(pwd, BCrypt.gensalt());
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
