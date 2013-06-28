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
import nl.wetzel.entities.User;
import nl.wetzel.exception.DuplicateEntityException;
import nl.wetzel.facades.UserFacadeLocal;

/**
 *
 * @author Timo
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @EJB
    private UserFacadeLocal userHelper;

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
        request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
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
        //get the variables
        String firstname = request.getParameter("txtFirstname");
        String lastname = request.getParameter("txtLastname");
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String password2 = request.getParameter("txtPassword2");

        ArrayList<String> errors = new ArrayList<String>();
        //error check
        if (firstname.isEmpty()) {
            errors.add("Firstname missing");
        }
        if (lastname.isEmpty()) {
            errors.add("Lastname missing");
        }
        if (email.isEmpty()) {
            errors.add("Email address missing");
        }
        if (password.isEmpty()) {
            errors.add("Password is missing");
        }
        if (password2.isEmpty()) {
            errors.add("Re-enter the password");
        }
        if (!password.isEmpty() && !password2.isEmpty() && !password.equals(password2)) {
            errors.add("Passwords do not match");
        }

        if (errors.size() > 0) {
            showError(request, response, firstname, lastname, email, password, password2, errors);
            return;
        }

        try {
            User user = userHelper.Register(firstname, lastname, email, password);
        } catch (RuntimeException ex) {
            Throwable t = ex.getCause();

            if (t instanceof DuplicateEntityException) {
                errors.add(t.getMessage());
            } else {
                errors.add("Something went wrong. Please contact support.");
            }
            showError(request, response, firstname, lastname, email, password, password2, errors);
            return;
        }

        request.getSession().setAttribute("registered", true);
        response.sendRedirect("/Wetzelplaats-war/login");

        //TODO Mail the password to the user
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String firstname, String lastname, String email, String password, String password2, ArrayList<String> errors) throws ServletException, IOException {
        request.setAttribute("firstname", firstname);
        request.setAttribute("lastname", lastname);
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("password2", password2);
        request.setAttribute("error", errors);
        request.getRequestDispatcher("/WEB-INF/login/register.jsp").forward(request, response);
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
