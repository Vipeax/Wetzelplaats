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
import nl.wetzel.facades.BidFacadeLocal;

@WebServlet(name = "MyAdsServlet", urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {

    @EJB
    private AdvertisementFacadeLocal advertisementFacade;
    @EJB
    private BidFacadeLocal bidFacade;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String oldreferer = (String) session.getAttribute("oldreferer");
               
        if(oldreferer != null)
        {
            session.setAttribute("oldreferer", null);
        }
        
        Integer adIndex = Convert.tryParseInt(request.getParameter("pa"));
        Integer bidIndex = Convert.tryParseInt(request.getParameter("pb"));                

        User user = (User) request.getSession().getAttribute("user");
        
        List ads = this.advertisementFacade.findByLimitAndUser(adIndex, Integer.valueOf(4) , user);
        int totalads = this.advertisementFacade.findByUserId(user).size();
        
        if(totalads%4 == 0)
        {
            totalads -= 1;
        }

        List bids = this.bidFacade.findByLimitAndUser(bidIndex, Integer.valueOf(4), user);
        int bidCount = this.bidFacade.findByUserId(user).size();
        
        if(bidCount%4 == 0)
        {
            bidCount -= 1;
        }
                      
        request.setAttribute("pa", adIndex);
        request.setAttribute("pb", bidIndex);
        request.setAttribute("adCount", Integer.valueOf(totalads));
        request.setAttribute("ads", ads);
        request.setAttribute("bidCount", Integer.valueOf(bidCount));
        request.setAttribute("bids", bids);

        request.getRequestDispatcher("/WEB-INF/account/account.jsp").forward(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}