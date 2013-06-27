package nl.wetzel.servlets;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.entities.Advertisement;
import nl.wetzel.facades.AdvertisementFacadeLocal;
import nl.wetzel.facades.BidFacadeLocal;

@WebServlet(name="AdvertisementDeleteServlet", urlPatterns={"/ad/delete"})
public class AdvertisementDeleteServlet extends HttpServlet
{

  @EJB
  private AdvertisementFacadeLocal advertisementFacade;

  @EJB
  private BidFacadeLocal bidFacade;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    int deleteId = Integer.parseInt(request.getParameter("did"));
    int admin = Integer.parseInt(request.getParameter("admin"));

    Advertisement ad = this.advertisementFacade.find(Integer.valueOf(deleteId));
    this.bidFacade.deleteById(ad);

    this.advertisementFacade.deleteById(deleteId);

    if (admin == 0)
      response.sendRedirect("/Wetzelplaats-war/account");
    else
      response.sendRedirect("/Wetzelplaats-war/admin");
  }

    /**
     *
     * @return
     */
    @Override
  public String getServletInfo()
  {
    return "Short description";
  }
}