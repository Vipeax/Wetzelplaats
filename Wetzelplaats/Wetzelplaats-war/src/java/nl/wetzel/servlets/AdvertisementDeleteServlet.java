package nl.wetzel.servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.entities.User;
import nl.wetzel.entities.User.UserType;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;

@WebServlet(name = "AdvertisementDeleteServlet", urlPatterns = {"/ad/delete"})
public class AdvertisementDeleteServlet extends HttpServlet {

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
            throws ServletException, IOException 
    {
        if (request.getParameter("did") == null) {
            response.sendRedirect("/Wetzelplaats-war/index");
            return;
        }
        
        User user = (User) request.getSession().getAttribute("user");
        int deleteId = Integer.parseInt(request.getParameter("did"));      
        Advertisement ad = this.advertisementFacade.find(Integer.valueOf(deleteId));
        
        if(ad.getUserId().getId() == user.getId())
        {
            this.bidFacade.deleteByAdId(ad);

            this.advertisementFacade.deleteById(ad);

            if (user.getUserType() == UserType.Admin) 
            {
                response.sendRedirect("/Wetzelplaats-war/account");
            } else {
                response.sendRedirect("/Wetzelplaats-war/admin");
            }
        }
        
        else
        {
            response.sendRedirect("/Wetzelplaats-war/MagicWord");
        } 
    }

       /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles advertisement deletion";
    }
}