package nl.wetzel.servlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.custom.Convert;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.UserFacadeLocal;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @EJB
    private AdvertisementFacadeLocal advertisementFacade;
    @EJB
    private UserFacadeLocal userFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer pageIndex = Convert.tryParseInt(request.getParameter("p"));
        int adCount = this.advertisementFacade.count();

        List ads = this.advertisementFacade.findByLimit(pageIndex, Integer.valueOf(4));

        int userCount = this.userFacade.count();
        List users = this.userFacade.findByLimit(pageIndex, Integer.valueOf(4));

        request.setAttribute("p", pageIndex);
        request.setAttribute("adCount", Integer.valueOf(adCount));
        request.setAttribute("ads", ads);
        request.setAttribute("userCount", Integer.valueOf(userCount));
        request.setAttribute("users", users);

        request.getRequestDispatcher("/WEB-INF/admin/admin.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}