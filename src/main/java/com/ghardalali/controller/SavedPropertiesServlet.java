package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.SavedProperty;
import com.ghardalali.model.User;
import com.ghardalali.service.SavedPropertyService;

/**
 * Servlet for handling saved properties in user dashboard
 */
@WebServlet("/dashboard/saved-properties")
public class SavedPropertiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private SavedPropertyService savedPropertyService;

    @Override
    public void init() throws ServletException {
        super.init();
        savedPropertyService = new SavedPropertyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Get saved properties for the user
        List<SavedProperty> savedProperties = savedPropertyService.getSavedPropertiesWithDetails(user.getUserId());

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("savedProperties", savedProperties);
        request.setAttribute("activeTab", "saved-properties");

        // Forward to saved properties page
        request.getRequestDispatcher("/saved-properties.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Get action parameter
        String action = request.getParameter("action");

        if ("save".equals(action)) {
            // Save property
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            boolean success = savedPropertyService.saveProperty(user.getUserId(), propertyId);

            // Redirect back to property detail page or properties page
            String referer = request.getParameter("referer");
            if (referer != null && !referer.isEmpty()) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/properties");
            }

        } else if ("remove".equals(action)) {
            // Remove saved property
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            boolean success = savedPropertyService.removeSavedProperty(user.getUserId(), propertyId);

            // Redirect back to saved properties page
            response.sendRedirect(request.getContextPath() + "/dashboard/saved-properties");
        } else {
            // Invalid action, redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
