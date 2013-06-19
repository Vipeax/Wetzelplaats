/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.tests;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;
import nl.wetzel.facades.AdvertisementFacade;
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
public class AdvertisementTest {

    AdvertisementFacade ad;
    EntityManager em;

    public AdvertisementTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ad = new AdvertisementFacade();
        em = mock(EntityManager.class);

        ad.setEm(em);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFindByLimit() {
        int amount = 4;
        int pageIndex = 0;

        //stub query
        TypedQuery<Advertisement> query = mock(TypedQuery.class);
        Mockito.when(em.createNamedQuery(AdvertisementFacade.FIND_ALL, Advertisement.class)).thenReturn(query);

        //stub the resultset
        List<Advertisement> ads = new LinkedList<Advertisement>();
        for (int i = 0; i < amount; i++) {
            ads.add(new Advertisement(i));
        }
        Mockito.when(query.getResultList()).thenReturn(ads);

        List<Advertisement> resultAds = ad.findByLimit(0, amount);

        verify(em).createNamedQuery(AdvertisementFacade.FIND_ALL, Advertisement.class);
        verify(query).getResultList();
        assertEquals(ads, resultAds);
    }

    @Test
    public void testFindByTitleCorrect() {
        String titleCorrect = "Advertisement1";

        //stub query
        TypedQuery<Advertisement> query = mock(TypedQuery.class);
        Mockito.when(em.createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class)).thenReturn(query);

        //stub getSingleResult
        Advertisement adStub = new Advertisement();
        adStub.setName(titleCorrect);
        Mockito.when(query.getSingleResult()).thenReturn(adStub);

        Advertisement result = ad.findByTitle(titleCorrect);

        verify(em).createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class);
        verify(query).getSingleResult();

        assertEquals(adStub, result);
    }

    @Test
    public void testFindByTitleNotFound() {
        String titleCorrect = "Advertisement1";

        //stub query
        TypedQuery<Advertisement> query = mock(TypedQuery.class);
        Mockito.when(em.createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class)).thenReturn(query);

        //stub getSingleResult
        Advertisement adStub = null;
        Mockito.when(query.getSingleResult()).thenReturn(adStub);

        Advertisement result = ad.findByTitle(titleCorrect);

        verify(em).createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class);
        verify(query).getSingleResult();

        assertEquals(adStub, result);
    }

    @Test
    public void testCreateAdvertisement() {
        String title = "ad test";
        String description = "this is a test";
        double price = 1.10;
        User user = new User();

        //stub query
        TypedQuery<Advertisement> query = mock(TypedQuery.class);
        Mockito.when(em.createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class)).thenReturn(query);

        //stub getSingleResult
        Advertisement adStub = new Advertisement(0, title, description, price, true, 1);
        //because the ad was not in the database, null is returned
        Mockito.when(query.getSingleResult()).thenReturn(null);

        //stub the persist
        Mockito.doNothing().when(em).persist(adStub);

        //launch the method
        Advertisement result = ad.createAdvertisement(title, description, price, user);

        verify(em).createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class);
        verify(query).getSingleResult();
        verify(em).persist(adStub);

        assertEquals(adStub, result);
    }

    @Test(expected = DuplicateEntityException.class)
    public void testCreateAdvertisementDuplicate() {
        String title = "ad test";
        String description = "this is a test";
        double price = 1.10;
        User user = new User();

        //stub query
        TypedQuery<Advertisement> query = mock(TypedQuery.class);
        Mockito.when(em.createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class)).thenReturn(query);

        //stub getSingleResult
        Advertisement adStub = new Advertisement(0, title, description, price, true, 1);
        //because the ad is in the database, adStub is returned. This will cause an exception :-)
        Mockito.when(query.getSingleResult()).thenReturn(adStub);

        //launch the method
        Advertisement result = ad.createAdvertisement(title, description, price, user);

        verify(em).createNamedQuery(AdvertisementFacade.FIND_BY_NAME, Advertisement.class);
        verify(query).getSingleResult();
    }

    @Test
    public void testCreateAdvertisementNoTitleOrDescription() {
        String title = null;
        String description = null;
        double price = 1.10;
        User user = new User();

        //launch the method
        Advertisement result = ad.createAdvertisement(title, description, price, user);

        assertNull(result);
    }

    @Test
    public void testAddBid() {
        Bid bid = new Bid();
        bid.setPrice(1.1);

        Advertisement adStub = new Advertisement();

        //stub the merge
        Mockito.when(em.merge(adStub)).thenReturn(adStub);

        Advertisement result = ad.addBid(adStub, bid);

        //add the bid to the ad stub, because we want to have it added as well
        adStub.getBidCollection().add(bid);

        verify(em).merge(adStub);

        assertSame(result, adStub);
    }
}