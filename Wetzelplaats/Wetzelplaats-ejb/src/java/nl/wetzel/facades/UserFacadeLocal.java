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
    
                             /**
     * Hashes password
     *
     * @return hashed password
     */
    String hashpwd(String pwd);

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();

                     /**
     * Finds user by email address
     *
     * @return user
     */
    public User findByEmail(String email);

    public void setEm(EntityManager em);

                 /**
     * Registers user
     *
     * @return user
     */
    public User Register(String firstname, String lastname, String email, String password);

    public User login(String email, String password);

    //Robert J.
            /**
     * Finds users by limit
     *
     * @return a list containing users
     */
    public List<User> findByLimit(Integer pageIndex, Integer amount);

                         /**
     * Deletes user by id
     *
     * @return 1: successful / -1: unsuccessful
     */
    public int deleteById(int id);
}
