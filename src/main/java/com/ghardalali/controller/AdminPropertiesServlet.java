package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.Property;
import com.ghardalali.model.User;
import com.ghardalali.service.PropertyService;

/**
 * Servlet for handling admin properties management
 */
@WebServlet("/admin/properties")
public class AdminPropertiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private PropertyService propertyService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        propertyService = new PropertyService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get user from session
        User user = (User) session.getAttribute("user");
        
        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        // Get all properties
        List<Property> properties = propertyService.getAllProperties();
        
        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("properties", properties);
        
        // Forward to admin properties page
        request.getRequestDispatcher("/admin-properties.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get user from session
        User user = (User) session.getAttribute("user");
        
        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        // Get action parameter
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            // Delete property
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            boolean success = propertyService.deleteProperty(propertyId);
            
            if (success) {
                request.setAttribute("successMessage", "Property deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete property");
            }
        }
        
        // Redirect back to admin properties page
        response.sendRedirect(request.getContextPath() + "/admin/properties");
    }
}
