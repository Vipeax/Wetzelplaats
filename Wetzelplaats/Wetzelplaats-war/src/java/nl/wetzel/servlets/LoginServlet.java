/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.servlets;

import com.internet.custom.BCrypt;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.wetzel.entities.User;
import nl.wetzel.facades.UserFacadeLocal;
import nl.wetzel.helpers.UserHelper;

/**
 *
 * @author Timo
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @EJB
//    private UserFacadeLocal userFacade;
    private UserHelper userHelper;

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
        HttpSession session = request.getSession();

        /* TODO: Check when user is already logged in and forward him to the index page or where ever he came from */
        if (session.getAttribute("user") != null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Boolean registered = (Boolean) session.getAttribute("registered");

        if (registered != null && registered) {
            request.setAttribute("registered", true);
            session.setAttribute("registered", null);
        }

        request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
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
        Boolean registerSuccess = (Boolean) request.getAttribute("registered");

        if (registerSuccess != null && registerSuccess) {
            doGet(request, response);
            return;
        }

        //Let's check if there even is a username and password
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");

        ArrayList<String> errors = new ArrayList<String>();
        if (email.isEmpty()) {
            errors.add("Enter email address");
        }
        if (password.isEmpty()) {
            errors.add("Enter password");
        }

        if (errors.size() > 0) {
            request.setAttribute("error", errors);

            request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
            return;
        }

        User u = userHelper.login(email, password);

        if (u != null) {
            request.getSession().setAttribute("user", u);

            response.sendRedirect("index.jsp");
        } else {
            errors.add("Username/password not found");
            request.setAttribute("error", errors);

            request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
        }

        //TODO finish the login shizzle by putting the login code to a custom manager! It's fugly in the post
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
