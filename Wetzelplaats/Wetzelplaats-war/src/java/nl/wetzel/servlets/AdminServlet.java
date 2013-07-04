package nl.wetzel.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.wetzel.custom.Convert;
import nl.wetzel.entities.User;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.UserFacadeLocal;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @EJB
    private AdvertisementFacadeLocal advertisementFacade;
    @EJB
    private UserFacadeLocal userFacade;

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
      HttpSession session = request.getSession();
        
      String oldreferer = (String) session.getAttribute("oldreferer");
               
      if(oldreferer != null)
      {
          session.setAttribute("oldreferer", null);
      }
      
      
      Integer pageIndexAd = Convert.tryParseInt(request.getParameter("pa"));
      Integer pageIndexUser = Convert.tryParseInt(request.getParameter("pu"));
      int adCount = this.advertisementFacade.count();
      
      if(adCount%4 == 0)
      {
          adCount -= 1;
      }

      List ads = this.advertisementFacade.findByLimit(pageIndexAd, Integer.valueOf(4));

      int userCount = this.userFacade.count();
      List users = this.userFacade.findByLimit(pageIndexUser, Integer.valueOf(4));
      
      if(userCount%4 == 0)
      {
          userCount -= 1;
      }

      request.setAttribute("pa", pageIndexAd);
      request.setAttribute("pu", pageIndexUser);
      request.setAttribute("adCount", Integer.valueOf(adCount));
      request.setAttribute("ads", ads);
      request.setAttribute("userCount", Integer.valueOf(userCount));
      request.setAttribute("users", users);      
        
       User u = (User) session.getAttribute("user");
       
       request.setAttribute("currentUser", u.getId());
       
        Boolean success = (Boolean) session.getAttribute("success");

        if (success != null && success) {
            request.setAttribute("success", true);
            session.setAttribute("success", null);
        }

      request.getRequestDispatcher("/WEB-INF/admin/admin.jsp").forward(request, response);
  }

     /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
  @Override
  public String getServletInfo()
  {
    return "Handles admin page";
  }
}
