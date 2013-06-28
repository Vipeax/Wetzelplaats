package nl.wetzel.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;
import nl.wetzel.facades.UserFacadeLocal;

@WebServlet(name = "UserDeleteServlet", urlPatterns = {"/user/delete"})
public class UserDeleteServlet extends HttpServlet {

    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private BidFacadeLocal bidFacade;
    @EJB
    private AdvertisementFacadeLocal advertisementFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int deleteId = Integer.parseInt(request.getParameter("did"));
        User user = this.userFacade.find(Integer.valueOf(deleteId));

        // Advertisement ad = bid.getAdvertisementId();
        //   this.advertisementFacade.removeBid(ad, bid);

        List<Bid> bids = bidFacade.findByUserId(user);

        for (Bid bid : bids) {
            Advertisement ad = bid.getAdvertisementId();
            ad.getBidCollection().remove(bid);
            advertisementFacade.edit(ad);
        }

        this.advertisementFacade.deleteByUserId(user);
        this.userFacade.deleteById(deleteId);
        response.sendRedirect("/Wetzelplaats-war/admin");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}