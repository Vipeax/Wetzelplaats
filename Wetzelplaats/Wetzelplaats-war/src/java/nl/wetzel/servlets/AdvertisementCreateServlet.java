/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;
import nl.wetzel.facades.AdvertisementFacadeLocal;

/**
 *
 * @author Timo
 */
@WebServlet(name = "AdvertisementCreateServlet", urlPatterns = {"/ad/create"})
public class AdvertisementCreateServlet extends HttpServlet {

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
        if (request.getSession().getAttribute("created") != null && (Boolean) request.getSession().getAttribute("created")) {
            request.setAttribute("created", true);
            request.getSession().setAttribute("created", null);
        }

        request.getRequestDispatcher("/WEB-INF/advertisement/create.jsp").forward(request, response);
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
        String title = request.getParameter("txtTitle");
        String description = request.getParameter("txtDescription");
        String priceStr = request.getParameter("txtPrice");
        double price = 0;
        HttpSession session = request.getSession();
        ArrayList<String> errors = new ArrayList<String>();

        if (title.isEmpty()) {
            errors.add("Provide a title");
        }
        if (description.isEmpty()) {
            errors.add("Provide a description");
        }
        if (priceStr.isEmpty()) {
            errors.add("Provide a minimum price");
        } else {
            //try parse the string to a double
            try {
                price = Double.parseDouble(priceStr);
            } catch (RuntimeException e) {
                errors.add("Enter a valid pirce, e.g. 123.12");
            }
        }

        if (errors.size() > 0) {
            setValues(title, description, priceStr, request, response, errors);
        } else {
            try {
                advertisementFacade.createAdvertisement(title, description, price, (User) session.getAttribute("user"));

                request.getSession().setAttribute("created", true);
                response.sendRedirect("/Wetzelplaats-war/ad/create");
            } catch (RuntimeException e) {
                Throwable t = e.getCause();

                if (t instanceof DuplicateEntityException) {
                    errors.add("Advertisement with the same title already exists. Please provide an other title");

                } else {
                    errors.add("Something went wrong. Please contact support");
                }
                setValues(title, description, priceStr, request, response, errors);
            }
        }
    }

    private void setValues(String title, String description, String priceStr, HttpServletRequest request, HttpServletResponse response, ArrayList<String> errors) throws ServletException, IOException {
        request.setAttribute("errors", errors);
        request.setAttribute("title", title);
        request.setAttribute("description", description);
        request.setAttribute("price", priceStr);
        request.getRequestDispatcher("/WEB-INF/advertisement/create.jsp").forward(request, response);
    }
    
        /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles advertisement creation";
    }// </editor-fold>
}
