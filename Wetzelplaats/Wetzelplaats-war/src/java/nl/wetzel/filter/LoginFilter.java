/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.wetzel.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.wetzel.entities.User;

/**
 *
 * @author Timo
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/ad/*", "/admin/*"})
public class LoginFilter implements Filter {

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        User u = (User) httpRequest.getSession().getAttribute("user");
        
        if (u == null) {

            if (httpRequest.getQueryString() == null) {
                //save the request path            
                httpRequest.getSession().setAttribute("path", httpRequest.getServletPath());
            } else {
                httpRequest.getSession().setAttribute("path", httpRequest.getServletPath() + "?" + httpRequest.getQueryString());
            }

            httpResponse.sendRedirect("/Wetzelplaats-war/login");
        } 
        else if (u.getUserType() != User.UserType.Admin && httpRequest.getServletPath().contains("admin"))
        {
            httpResponse.sendRedirect("/Wetzelplaats-war/index");
        }
        else 
        {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
