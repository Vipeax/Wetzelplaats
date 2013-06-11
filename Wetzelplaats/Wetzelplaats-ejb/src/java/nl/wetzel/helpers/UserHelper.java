/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.helpers;

import com.internet.custom.BCrypt;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import nl.wetzel.entities.User;
import nl.wetzel.facades.UserFacade;
import nl.wetzel.facades.UserFacadeLocal;

/**
 *
 * @author Timo
 */
@Stateless
@LocalBean
public class UserHelper {

    @EJB
    private UserFacadeLocal userFacade;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">    
    public UserHelper() {
    }

    public UserHelper(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
    //</editor-fold>

    public User login(String email, String password) {
        User u = getUserFacade().getUser(email);

        if (u != null) {
            if (!BCrypt.checkpw(password, u.getPassword())) {
                u = null;
            }
        }

        return u;
    }    
    
    public void Register(String firstname, String lastname, String email, String password) {
        User u = new User();
        u.setFirstname(firstname);
        u.setLastname(lastname);
        u.setEmail(email);
        u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        
        userFacade.create(u);        
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    public void setUserFacade(UserFacadeLocal userFacade) {
        this.userFacade = userFacade;
    }

    public UserFacadeLocal getUserFacade() {
        if (userFacade == null) {
            userFacade = new UserFacade();
        }

        return userFacade;
    }
    //</editor-fold>
}
