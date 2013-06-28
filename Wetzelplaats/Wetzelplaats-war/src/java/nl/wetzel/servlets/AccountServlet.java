package nl.wetzel.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.custom.Convert;
import nl.wetzel.entities.User;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;

@WebServlet(name = "MyAdsServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    @EJB
    private AdvertisementFacadeLocal advertisementFacade;
    @EJB
    private BidFacadeLocal bidFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer pageIndex = Convert.tryParseInt(request.getParameter("p"));

        User user = (User) request.getSession().getAttribute("user");
        List ads = this.advertisementFacade.findByUserId(user);
        int adCount = ads.size();

        List bids = this.bidFacade.findByUserId(user);
        int bidCount = bids.size();

        request.setAttribute("p", pageIndex);
        request.setAttribute("adCount", Integer.valueOf(adCount));
        request.setAttribute("ads", ads);
        request.setAttribute("bidcount", Integer.valueOf(bidCount));
        request.setAttribute("bids", bids);
        request.getRequestDispatcher("/WEB-INF/account/account.jsp").forward(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}