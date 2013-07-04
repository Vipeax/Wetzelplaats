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
import nl.wetzel.facades.UserFacadeLocal;

/**
 *
 * @author R. Wetzels
 */
@WebServlet(name = "UserEditServlet", urlPatterns = {"/user/edit"})
public class UserEditServlet extends HttpServlet {
   
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
        int userId = Integer.parseInt(request.getParameter("id"));

        User user = userHelper.find(userId);
                        
        request.setAttribute("olduser", user);
        
        HttpSession session = request.getSession();
        
        Boolean success = (Boolean) session.getAttribute("success");

        if (success != null && success) {
            request.setAttribute("success", true);
            session.setAttribute("success", null);
        }
        
        request.getRequestDispatcher("/WEB-INF/admin/user/edit.jsp").forward(request, response);
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
            throws ServletException, IOException 
    {
        int userId = Integer.parseInt(request.getParameter("userid"));
        //get the variables
        User u = userHelper.find(userId);
        
        String firstname = request.getParameter("txtFirstname");
        String lastname = request.getParameter("txtLastname");
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String password2 = request.getParameter("txtPassword2");
        String role = request.getParameter("userType");                

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
        try 
        {
            if(errors.isEmpty())
            {
                u.setFirstname(firstname);
                u.setLastname(lastname);
                u.setEmail(email);                
                u.setPassword(userHelper.hashpwd(password));  
                
                if("Admin".equals(role))
                {
                    u.setUserType(User.UserType.Admin);
                }
                else if("User".equals(role))
                {
                    u.setUserType(User.UserType.User);
                }
                else
                {
                    u.setUserType(User.UserType.Guest);
                }
                
                userHelper.edit(u);
            }  
        } catch (RuntimeException ex) 
        {
            errors.add("Something went wrong. Please contact support.");
        }
        finally 
        {                
            if (errors.size() > 0) 
            {
                showError(request, response, userId, firstname, lastname, email, errors);
                return;
            }         
        }                            
        request.getSession().setAttribute("success", true);
        response.sendRedirect("/Wetzelplaats-war/admin");
    }
    
      private void showError(HttpServletRequest request, HttpServletResponse response, int id,  String firstname, String lastname, String email, ArrayList<String> errors) throws ServletException, IOException {
        User u = new User(id, firstname, lastname, email, null);
        request.setAttribute("olduser", u);
        request.setAttribute("error", errors);
        request.getRequestDispatcher("/WEB-INF/admin/user/edit.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles user editing";
    }// </editor-fold>
}
