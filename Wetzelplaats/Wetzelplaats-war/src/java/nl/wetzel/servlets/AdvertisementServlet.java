/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.custom.Convert;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.entities.User;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;

/**
 *
 * @author timo
 */
@WebServlet(name = "AdvertisementServlet", urlPatterns = {"/ad/view"})
public class AdvertisementServlet extends HttpServlet {

    @EJB
    private BidFacadeLocal bidFacade;
    @EJB
    private AdvertisementFacadeLocal advertisementFacade;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //try get advertiser ID from queryString
        if (request.getParameter("id") == null) {
            response.sendRedirect("/Wetzelplaats-war/index");
            return;
        }

        Integer adId = Convert.tryParseInt((String) request.getParameter("id"));

        Advertisement ad = advertisementFacade.find(adId);
//        //        List<Bid> bidCollection = bidHelper.getBidFacade().findByAdvertisementId(adId);
        List<Bid> bidCollection = (List) ad.getBidCollection();

        request.setAttribute("ad", ad);
        request.setAttribute("ad.bidCollection", bidCollection);
        request.getRequestDispatcher("/WEB-INF/advertisement/view.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //first things first, let's try to parse the price
        String priceStr = (String) request.getParameter("txtBid");
        double price = Convert.tryParseDouble(priceStr);
        ArrayList<String> errors = new ArrayList<String>();

        int adId = Integer.parseInt(request.getParameter("ad"));

        Advertisement ad = advertisementFacade.find(adId);
        User user = (User) request.getSession().getAttribute("user");

        double highestBid = 0;

        if (ad.getBidCollection() != null && ad.getBidCollection().size() > 0) {
            List<Bid> bidCollection = (List) ad.getBidCollection();
            highestBid = bidCollection.get(0).getPrice();;
        }

        //I know it's ugly to check on 0, but it's convenient because a bid can NEVER be 0
        if (price == 0 || price <= highestBid) {
            errors.add("Price must be higher than 0 or the current highest bid.");
        } else {
            Bid bid = bidFacade.createBid(price, user, ad);

            if (bid == null) {
                errors.add("uh oh! Something went wrong.");
            }
        }
        if (errors.size() > 0) {
            //don't forget to add the ad again
            request.setAttribute("ad", ad);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/advertisement/view.jsp").forward(request, response);
        } else {
            //success, redirect instead of dispatcher
            response.sendRedirect("/Wetzelplaats-war/ad/view?id=" + ad.getId());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
