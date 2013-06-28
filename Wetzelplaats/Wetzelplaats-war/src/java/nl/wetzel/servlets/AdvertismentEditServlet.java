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

/**
 *
 * @author Robert
 */
@WebServlet(name = "AdvertismentEditServlet", urlPatterns = {"/ad/edit"})
public class AdvertismentEditServlet extends HttpServlet {
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
            throws ServletException, IOException 
    {
        if (request.getParameter("eId") == null) {
            response.sendRedirect("/Wetzelplaats-war/index");
            return;
        }

        Integer adId = Convert.tryParseInt((String) request.getParameter("eId"));
        Advertisement ad = advertisementFacade.find(adId);
          
        request.setAttribute("ad", ad);
        request.getRequestDispatcher("/WEB-INF/advertisement/edit.jsp").forward(request, response);
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
        //parse parameters
        String title = (String) request.getParameter("txTitle");
        String description = (String) request.getParameter("txtDescription");
        String priceStr = (String) request.getParameter("txtPrice");
        double price = Convert.tryParseDouble(priceStr);
        
        ArrayList<String> errors = new ArrayList<String>();
        
        int adId = Integer.parseInt(request.getParameter("ad"));
        Advertisement ad = advertisementFacade.find(adId);
        
        if (price == 0)
        {
            errors.add("Price must be higher than 0.");
        }
        if ("".equals(title) || title == null)
        {
            errors.add("Please enter a title.");
        }
        if ("".equals(description) || description == null)
        {
            errors.add("Please enter a description.");
        }   
        
        if(errors.isEmpty())
        {
            ad.setName(title);
            ad.setDescription(description);
            ad.setPrice(price);
            advertisementFacade.edit(ad);
        }
        
        if (errors.size() > 0) {
            //don't forget to add the ad again
            request.setAttribute("ad", ad);
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/advertisement/edit.jsp").forward(request, response);
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
