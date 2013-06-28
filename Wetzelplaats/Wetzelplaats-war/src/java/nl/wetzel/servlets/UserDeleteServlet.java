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
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;
import nl.wetzel.facades.UserFacadeLocal;

@WebServlet(name="UserDeleteServlet", urlPatterns={"/user/delete"})
public class UserDeleteServlet extends HttpServlet
{

  @EJB
  private UserFacadeLocal userFacade;

  @EJB
  private BidFacadeLocal bidFacade;

  @EJB
  private AdvertisementFacadeLocal advertisementFacade;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    int deleteId = Integer.parseInt(request.getParameter("did"));

    Advertisement ad = this.advertisementFacade.find(Integer.valueOf(deleteId));
    this.bidFacade.deleteByAdId(ad);

    User user = this.userFacade.find(Integer.valueOf(deleteId));
    this.advertisementFacade.deleteByUserId(user);

    this.userFacade.deleteById(deleteId);
    response.sendRedirect("/Wetzelplaats-war/admin");
  }

  @Override
  public String getServletInfo()
  {
    return "Short description";
  }
}