/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.helpers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.User;
import nl.wetzel.facades.AdvertisementFacade;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Timo
 */
public class AdvertisementHelperTest {
    
    @EJB
    private AdvertisementHelper helper;
    
    @EJB
    private AdvertisementFacade facade;
    
    public AdvertisementHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        helper = new AdvertisementHelper();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testFindAll() {
        List<Advertisement> ads = facade.findAll();
    }

//    /**
//     * Test of createAdvertisement method, of class AdvertisementHelper.
//     */
//    @Test
//    public void testCreateAdvertisement() throws Exception {
//        User u = new User();
//        u.setFirstname("Timo");
//        u.setLastname("Hermans");
//        u.setEmail("tmhermans@gmail.com");
//        u.setId(1);
//        
//       helper.createAdvertisement("hallo", "nog een hallo", 123L, u);
//    }

}