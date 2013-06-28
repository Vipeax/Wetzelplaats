/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.tests;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.facades.AdvertisementFacade;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacade;
import nl.wetzel.facades.BidFacadeLocal;
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
public class BidTest {

    private BidFacadeLocal bidFacade;
    private EntityManager em;
    private AdvertisementFacadeLocal advertisementFacade;

    public BidTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        em = mock(EntityManager.class);

        advertisementFacade = new AdvertisementFacade();
        advertisementFacade.setEm(em);

        bidFacade = new BidFacade();
        bidFacade.setEm(em);
        bidFacade.setAdvertisementFacade(advertisementFacade);

    }

    @Test
    public void testFindByAdvertisementId() {
        int advertisementId = 1;

        Advertisement ad = new Advertisement(advertisementId);
        Bid bidStub = new Bid(1);
        bidStub.setAdvertisementId(ad);

        //stub the creation of the query
        TypedQuery<Bid> query = mock(TypedQuery.class);
        Mockito.when(em.createNamedQuery(BidFacade.FIND_BY_ADVERTISEMENT_ID, Bid.class)).thenReturn(query);

        //stub the getResultList
        List<Bid> bidStubList = new LinkedList<Bid>();
        bidStubList.add(bidStub);
        Mockito.when(query.getResultList()).thenReturn(bidStubList);

        //call the method
        List<Bid> result = bidFacade.findByAdvertisementId(advertisementId);

        //verify the calls
        verify(em).createNamedQuery(BidFacade.FIND_BY_ADVERTISEMENT_ID, Bid.class);
        verify(query).getResultList();

        //assert
        assertSame(result, bidStubList);
    }

    @Test
    public void createBidTest() {
        double price = 0.01;
        User user = new User();
        Advertisement ad = new Advertisement();

        //stub the merge
        when(em.merge(ad)).thenReturn(ad);

        //stub the persist
        Bid bidStub = new Bid();
        bidStub.setPrice(price);
        bidStub.setUserId(user);
        bidStub.setAdvertisementId(ad);
        doNothing().when(em).persist(bidStub);

        //call the method
        Bid result = bidFacade.createBid(price, user, ad);

        //at this point, a bid was added, so make an expected Bid
        Bid bidExpected = new Bid();
        User userExpected = new User();
        Advertisement adExpected = new Advertisement();

        bidExpected.setPrice(price);
        bidExpected.setAdvertisementId(adExpected);
        bidExpected.setUserId(userExpected);

        List<Bid> bidList = new LinkedList<Bid>();
        bidList.add(bidExpected);
        adExpected.setBidCollection(bidList);

        //verify the stubs
        verify(em).merge(ad);
        verify(em).persist(bidStub);

        //assert
        assertEquals(result, bidExpected);
    }
}