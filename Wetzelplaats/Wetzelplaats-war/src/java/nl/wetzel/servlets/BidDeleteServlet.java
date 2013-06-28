/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.Bid;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;

/**
 *
 * @author Robert
 */
@WebServlet(name = "BidDeleteServlet", urlPatterns = {"/bid/delete"})
public class BidDeleteServlet extends HttpServlet {

    @EJB
    private AdvertisementFacadeLocal advertisementFacade;
    @EJB
    private BidFacadeLocal bidFacade;

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
        int bidId = Integer.parseInt(request.getParameter("bId"));

        Bid bid = bidFacade.find(bidId);
        Advertisement ad = bid.getAdvertisementId();
        this.advertisementFacade.removeBid(ad, bid);
        this.bidFacade.remove(bid);
        response.sendRedirect("/Wetzelplaats-war/account");
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
