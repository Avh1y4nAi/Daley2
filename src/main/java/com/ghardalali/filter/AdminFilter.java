package com.ghardalali.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.util.SessionUtil;

/**
 * Filter to check if user is an admin for admin-protected pages
 */
@WebFilter(urlPatterns = { "/admin/*" })
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if user is logged in
        if (SessionUtil.isLoggedIn(httpRequest)) {
            User user = SessionUtil.getCurrentUser(httpRequest);

            if (user.isAdmin()) {
                // User is admin, continue with the request
                chain.doFilter(request, response);
            } else {
                // User is not admin, redirect to regular dashboard with access denied message
                HttpSession session = httpRequest.getSession(true);
                session.setAttribute("errorMessage", "Access denied. You do not have administrator privileges.");
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard");
            }
        } else {
            // User is not logged in, store the original request URL for redirect after
            // login
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("redirectAfterLogin", httpRequest.getRequestURI());
            session.setAttribute("errorMessage", "Please login to access the admin area.");

            // Redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
