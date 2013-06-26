/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.tests;

import com.internet.custom.BCrypt;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;
import nl.wetzel.facades.UserFacade;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author Timo
 */
public class UserTest {

    private UserFacade userFacade;
    private EntityManager em;
    private TypedQuery<User> query;

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        userFacade = new UserFacade();

        em = mock(EntityManager.class);
        userFacade.setEm(em);

        query = mock(TypedQuery.class);
        when(em.createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class)).thenReturn(query);
    }

    @Test
    public void testFindByEmailFound() {
        String email = "tmhermans@gmail.com";
        //stub a user to return
        User user = new User();
        user.setFirstname("Timo");
        user.setLastname("Hermans");
        user.setPassword("Test");
        user.setEmail(email);
        when(query.getSingleResult()).thenReturn(user);

        //call method
        User result = userFacade.findByEmail(email);

        //verify
        verify(em).createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class);
        verify(query).getSingleResult();

        //assert
        assertSame(user, result);
    }

    @Test
    public void testFindByEmailNotFound() {
        String email = "tmhermans@gmail.com";
        //stub a user to return
        User user = new User();
        user.setFirstname("Timo");
        user.setLastname("Hermans");
        user.setPassword("Test");
        user.setEmail(email);
        when(query.getSingleResult()).thenReturn(null);

        //call method
        User result = userFacade.findByEmail(email);

        //verify
        verify(em).createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class);
        verify(query).getSingleResult();

        //assert
        assertNull(result);
    }

    @Test
    public void testLogin() {
        String email = "tmhermans@gmail.com";
        String password = "test";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //stub a user to return
        User user = new User();
        user.setFirstname("Timo");
        user.setLastname("Hermans");
        user.setPassword(hashedPassword);
        user.setEmail(email);
        when(query.getSingleResult()).thenReturn(user);

        //execute method
        User result = userFacade.login(email, password);

        //verify
        verify(em).createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class);
        verify(query).getSingleResult();

        //assert
        assertSame(result, user);
    }

    @Test
    public void testLoginWrongPassword() {
        String email = "tmhermans@gmail.com";
        String password = "test";
        String hashedPassword = BCrypt.hashpw("foutWachtwoord", BCrypt.gensalt());

        //stub a user to return
        User user = new User();
        user.setFirstname("Timo");
        user.setLastname("Hermans");
        user.setPassword(hashedPassword);
        user.setEmail(email);
        when(query.getSingleResult()).thenReturn(user);

        //execute method
        User result = userFacade.login(email, password);

        //verify
        verify(em).createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class);
        verify(query).getSingleResult();

        //assert
        assertNull(result);
    }

    @Test
    public void testRegisterSuccess() {
        String email = "tmhermans@gmail.com";
        String firstname = "Timo";
        String lastname = "Hermans";
        String password = "Password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //stub a user to return
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        when(query.getSingleResult()).thenReturn(null);

        //stub the persist
        doNothing().when(em).persist(user);

        //call method
        User result = userFacade.Register(firstname, lastname, email, password);

        //verify
        verify(em).createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class);
        verify(query).getSingleResult();
        verify(em).persist(user);

        //assert
        assertNotNull(result);
    }

    @Test(expected = DuplicateEntityException.class)
    public void testRegisterDuplicate() {
        String email = "tmhermans@gmail.com";
        String firstname = "Timo";
        String lastname = "Hermans";
        String password = "Password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //stub a user to return
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        when(query.getSingleResult()).thenReturn(user);

        //call method
        User result = userFacade.Register(firstname, lastname, email, password);

        //verify
        verify(em).createNamedQuery(UserFacade.FIND_BY_EMAIL, User.class);
        verify(query).getSingleResult();
    }

    @Test
    public void testRegisterMissingField() {
        String email = null;
        String firstname = null;
        String lastname = null;
        String password = null;
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //call method
        User result = userFacade.Register(firstname, lastname, email, password);

        //assert
        assertNull(result);
    }
}