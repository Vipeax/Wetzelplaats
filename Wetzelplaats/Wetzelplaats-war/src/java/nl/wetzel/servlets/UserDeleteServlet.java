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
            response.sendRedirect("/Wetzelplaats-war/admin");
            return;
        }
        
        User userReq = (User) request.getSession().getAttribute("user");
        
        if (userReq.getUserType() == User.UserType.Admin) 
        {
            int deleteId = Integer.parseInt(request.getParameter("did"));
            User user = this.userFacade.find(Integer.valueOf(deleteId));

            // Advertisement ad = bid.getAdvertisementId();
            //   this.advertisementFacade.removeBid(ad, bid);

            bidFacade.deleteByUserId(user);

            List<Advertisement> ads = advertisementFacade.findByUserId(user);

            for(Advertisement ad : ads)
            {
                bidFacade.deleteByAdId(ad);
            }        

            this.advertisementFacade.deleteByUserId(user);

            this.userFacade.deleteById(deleteId);

            response.sendRedirect("/Wetzelplaats-war/admin");
        }
    }

        /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles user deletion";
    }
}