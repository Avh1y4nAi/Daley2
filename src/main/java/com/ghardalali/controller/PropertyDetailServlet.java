package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ghardalali.model.Property;
import com.ghardalali.service.PropertyService;

/**
 * Servlet for handling property detail page
 */
@WebServlet("/property-detail")
public class PropertyDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PropertyService propertyService;
    
    @Override
    public void init() throws ServletException {
        propertyService = new PropertyService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get property ID from request parameter
        String propertyIdStr = request.getParameter("id");
        
        if (propertyIdStr == null || propertyIdStr.trim().isEmpty()) {
            // Redirect to properties page if no ID is provided
            response.sendRedirect(request.getContextPath() + "/properties");
            return;
        }
        
        try {
            int propertyId = Integer.parseInt(propertyIdStr);
            
            // Get property by ID
            Property property = propertyService.getPropertyById(propertyId);
            
            if (property == null) {
                // Redirect to properties page if property is not found
                response.sendRedirect(request.getContextPath() + "/properties");
                return;
            }
            
            // Get similar properties
            List<Property> similarProperties = propertyService.getSimilarProperties(propertyId, 2);
            
            // Set attributes for JSP
            request.setAttribute("property", property);
            request.setAttribute("similarProperties", similarProperties);
            
            // Forward to property-detail.jsp
            request.getRequestDispatcher("/property-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Redirect to properties page if ID is not a valid number
            response.sendRedirect(request.getContextPath() + "/properties");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
