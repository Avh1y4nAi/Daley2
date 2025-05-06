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
 * Servlet for handling properties listing and search
 */
@WebServlet("/properties")
public class PropertiesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PropertyService propertyService;
    
    @Override
    public void init() throws ServletException {
        propertyService = new PropertyService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get search parameters
        String keyword = request.getParameter("search-keyword");
        String propertyType = request.getParameter("property-type");
        String minPriceStr = request.getParameter("min-price");
        String maxPriceStr = request.getParameter("max-price");
        String bedroomsStr = request.getParameter("bedrooms");
        String bathroomsStr = request.getParameter("bathrooms");
        String status = request.getParameter("property-status");
        String sortBy = request.getParameter("sort-by");
        
        // Parse numeric parameters
        Double minPrice = null;
        Double maxPrice = null;
        Integer bedrooms = null;
        Integer bathrooms = null;
        
        try {
            if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                minPrice = Double.parseDouble(minPriceStr);
            }
            
            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceStr);
            }
            
            if (bedroomsStr != null && !bedroomsStr.trim().isEmpty()) {
                bedrooms = Integer.parseInt(bedroomsStr);
            }
            
            if (bathroomsStr != null && !bathroomsStr.trim().isEmpty()) {
                bathrooms = Integer.parseInt(bathroomsStr);
            }
        } catch (NumberFormatException e) {
            // Ignore parsing errors and use null values
        }
        
        // Search properties
        List<Property> properties = propertyService.searchProperties(keyword, propertyType, minPrice, maxPrice,
                                                                   bedrooms, bathrooms, status, sortBy);
        
        // Set attributes for JSP
        request.setAttribute("properties", properties);
        request.setAttribute("propertiesCount", properties.size());
        request.setAttribute("keyword", keyword);
        request.setAttribute("propertyType", propertyType);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("bedrooms", bedrooms);
        request.setAttribute("bathrooms", bathrooms);
        request.setAttribute("status", status);
        request.setAttribute("sortBy", sortBy);
        
        // Forward to properties.jsp
        request.getRequestDispatcher("/properties.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
