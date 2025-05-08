package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ghardalali.model.Property;
import com.ghardalali.service.PropertyService;

/**
 * Servlet for handling property detail page
 */
@WebServlet("/property-detail")
public class PropertyDetailServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private PropertyService propertyService;

    @Override
    public void init() throws ServletException {
        propertyService = new PropertyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pre-process request
        preProcessRequest(request, response);

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

            // Get property gallery data
            Map<String, Object> galleryData = propertyService.getPropertyGalleryData(propertyId);

            // Set attributes for JSP
            request.setAttribute("property", property);
            request.setAttribute("similarProperties", similarProperties);
            request.setAttribute("mainImage", galleryData.get("mainImage"));
            request.setAttribute("allImages", galleryData.get("allImages"));

            // Set active navigation tab
            setActiveTab(request, "properties");

            // Forward to property-detail.jsp
            request.getRequestDispatcher("/property-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Redirect to properties page if ID is not a valid number
            response.sendRedirect(request.getContextPath() + "/properties");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pre-process request
        preProcessRequest(request, response);

        // Get property ID from request parameter
        String propertyIdStr = request.getParameter("id");
        String action = request.getParameter("action");
        String imagePath = request.getParameter("imagePath");

        if (propertyIdStr == null || propertyIdStr.trim().isEmpty()) {
            // Redirect to properties page if no ID is provided
            response.sendRedirect(request.getContextPath() + "/properties");
            return;
        }

        try {
            int propertyId = Integer.parseInt(propertyIdStr);

            // Handle different actions
            if ("selectImage".equals(action) && imagePath != null && !imagePath.trim().isEmpty()) {
                // Set the selected image as the main image
                boolean success = propertyService.setPrimaryImage(propertyId, imagePath);

                if (success) {
                    setFlashSuccessMessage(request, "Main image updated successfully");
                } else {
                    setFlashErrorMessage(request, "Failed to update main image");
                }
            }

            // Redirect back to the property detail page
            response.sendRedirect(request.getContextPath() + "/property-detail?id=" + propertyId);
        } catch (NumberFormatException e) {
            // Redirect to properties page if ID is not a valid number
            response.sendRedirect(request.getContextPath() + "/properties");
        }
    }
}
