/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.tests;

import com.internet.custom.BCrypt;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;
import nl.wetzel.facades.UserFacade;
import nl.wetzel.facades.UserFacadeLocal;
import nl.wetzel.helpers.UserHelper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author Timo
 */
public class TestUser {
    @EJB
    private UserHelper helper;
    private UserFacadeLocal localMock;
    private User u;
    private String rightEmail = "tmhermans@gmail.com";
    private String wrongEmail = "blaat@gmaal.com";
    private String rightPassword = "test";
    private String wrongPassword = "tets";
    private String firstname = "Timo";
    private String lastname = "Hermans";
    private List<Integer> userCount;
    
    public TestUser() {
    }

    @Before
    public void setUp() {
        //set up a nice mock
        u = new User();
        u.setEmail(rightEmail);
        u.setPassword(BCrypt.hashpw(rightPassword, BCrypt.gensalt()));
//        u.setId(1);

        localMock = mock(UserFacadeLocal.class);
        //mock a legit user
        when(localMock.getUser(rightEmail)).thenReturn(null);
        //mock wrong password
        when(localMock.getUser(wrongEmail)).thenReturn(null);
        //mock register
        Mockito.doNothing().when(localMock).create(u);

        helper = new UserHelper();
        helper.setUserFacade(localMock);
    }
    
    @Test
    public void testLoginCorrect() {
        when(localMock.getUser(rightEmail)).thenReturn(u);

        User result = helper.login(rightEmail, rightPassword);

        verify(localMock).getUser(rightEmail);
        assertEquals(u, result);
    }

    @Test
    public void testLoginFalseUsername() {
        when(localMock.getUser(rightEmail)).thenReturn(u);

        User result = helper.login(wrongEmail, rightPassword);

        verify(localMock).getUser(wrongEmail);
        assertEquals(null, result);
    }

    @Test
    public void testLoginFalsePassword() {
        when(localMock.getUser(rightEmail)).thenReturn(u);

        User result = helper.login(rightEmail, wrongPassword);

        verify(localMock).getUser(rightEmail);
        assertEquals(null, result);
    }

    @Test
    public void testRegisterCorrect() {
        User created = helper.Register(firstname, lastname, rightEmail, rightPassword);

        verify(localMock).create(u);
        assertEquals(u, created);
    }

    @Test
    public void testRegisterMissingFirstname() {
        User created = helper.Register(null, lastname, rightEmail, rightPassword);

        verify(localMock, times(0)).create(u);
        assertEquals(null, created);
    }

    @Test
    public void testRegisterMissingLastname() {
        User created = helper.Register(firstname, null, rightEmail, rightPassword);

        verify(localMock, times(0)).create(u);
        assertEquals(null, created);
    }

    @Test
    public void testRegisterMissingEmail() {
        User created = helper.Register(firstname, lastname, null, rightPassword);

        verify(localMock, times(0)).create(u);
        assertEquals(null, created);
    }

    @Test(expected = DuplicateEntityException.class)
    public void testRegisterExistingUser() {
        User createdFirst = helper.Register(firstname, lastname, rightEmail, rightPassword);

        //fake it so that the database already owns the user
        when(localMock.getUser(rightEmail)).thenReturn(u);

        User createdSecond = helper.Register(firstname, lastname, rightEmail, rightPassword);

        verify(localMock, times(1)).create(u);
    }

    @Test
    public void testRegisterMissingPassword() {
        User created = helper.Register(firstname, lastname, rightEmail, null);

        verify(localMock, times(0)).create(u);
        assertEquals(null, created);
    }
}