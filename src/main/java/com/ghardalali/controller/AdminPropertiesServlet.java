package com.ghardalali.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.ghardalali.util.SessionUtil;

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

        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Generate CSRF token for forms if needed
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Check for action parameter
        String action = request.getParameter("action");

        if ("new".equals(action)) {
            // Forward to add property form
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "properties");
            request.getRequestDispatcher("/admin-add-property.jsp").forward(request, response);
            return;
        } else if ("view".equals(action)) {
            try {
                // Get property ID
                int propertyId = Integer.parseInt(request.getParameter("propertyId"));

                // Get property details
                Property property = propertyService.getPropertyById(propertyId);

                if (property == null) {
                    // Property not found
                    session.setAttribute("errorMessage", "Property not found");
                    response.sendRedirect(request.getContextPath() + "/admin/properties");
                    return;
                }

                // Set attributes for JSP
                request.setAttribute("user", user);
                request.setAttribute("property", property);
                request.setAttribute("activeTab", "properties");

                // Forward to view property page
                request.getRequestDispatcher("/admin-view-property.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "Error viewing property: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/admin/properties");
                return;
            }
        } else if ("edit".equals(action)) {
            // Get property ID
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));

            // Get property details
            Property property = propertyService.getPropertyById(propertyId);

            if (property == null) {
                // Property not found
                request.setAttribute("errorMessage", "Property not found");
                response.sendRedirect(request.getContextPath() + "/admin/properties");
                return;
            }

            // Set attributes for JSP
            request.setAttribute("user", user);
            request.setAttribute("property", property);
            request.setAttribute("activeTab", "properties");

            // Forward to edit property page
            request.getRequestDispatcher("/admin-edit-property.jsp").forward(request, response);
            return;
        }

        // Get all properties
        List<Property> properties = propertyService.getAllProperties();

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("properties", properties);
        request.setAttribute("activeTab", "properties");

        // Forward to admin properties page
        request.getRequestDispatcher("/admin-properties.jsp").forward(request, response);
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

        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            session.setAttribute("errorMessage", "Invalid CSRF token. Please try again.");
            response.sendRedirect(request.getContextPath() + "/admin/properties");
            return;
        }

        // Get action parameter
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Delete property
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            boolean success = propertyService.deleteProperty(propertyId);

            if (success) {
                session.setAttribute("successMessage", "Property deleted successfully");
            } else {
                session.setAttribute("errorMessage", "Failed to delete property");
            }

            // Redirect back to admin properties page
            response.sendRedirect(request.getContextPath() + "/admin/properties");
            return;
        } else if ("add".equals(action) || "update".equals(action)) {
            // Add new property or update existing property
            try {
                // Get form parameters
                String propertyName = request.getParameter("propertyName");
                String propertyType = request.getParameter("propertyType");
                String description = request.getParameter("description");
                String location = request.getParameter("location");
                String priceStr = request.getParameter("price");
                String bedroomsStr = request.getParameter("bedrooms");
                String bathroomsStr = request.getParameter("bathrooms");
                String sizeStr = request.getParameter("size");
                String sizeUnit = request.getParameter("sizeUnit");
                String status = request.getParameter("status");
                String imagePath = request.getParameter("imagePath");

                // Get property ID for update
                String propertyIdStr = request.getParameter("propertyId");
                int propertyId = 0;
                if (propertyIdStr != null && !propertyIdStr.trim().isEmpty()) {
                    propertyId = Integer.parseInt(propertyIdStr);
                }

                // Validate required fields
                if (propertyName == null || propertyName.trim().isEmpty() ||
                        propertyType == null || propertyType.trim().isEmpty() ||
                        description == null || description.trim().isEmpty() ||
                        location == null || location.trim().isEmpty() ||
                        priceStr == null || priceStr.trim().isEmpty() ||
                        status == null || status.trim().isEmpty()) {

                    String errorPage = "add".equals(action) ? "/admin-add-property.jsp" : "/admin-edit-property.jsp";
                    request.setAttribute("errorMessage", "Please fill in all required fields");
                    request.setAttribute("user", user);
                    request.setAttribute("activeTab", "properties");

                    if ("update".equals(action) && propertyId > 0) {
                        Property property = propertyService.getPropertyById(propertyId);
                        request.setAttribute("property", property);
                    }

                    request.getRequestDispatcher(errorPage).forward(request, response);
                    return;
                }

                // Create Property object
                Property property = new Property();
                if ("update".equals(action) && propertyId > 0) {
                    // For update, get existing property first
                    property = propertyService.getPropertyById(propertyId);
                    if (property == null) {
                        request.setAttribute("errorMessage", "Property not found");
                        response.sendRedirect(request.getContextPath() + "/admin/properties");
                        return;
                    }
                }

                // Set property fields
                property.setPropertyName(propertyName);
                property.setPropertyType(propertyType);
                property.setDescription(description);
                property.setLocation(location);
                property.setPrice(new BigDecimal(priceStr));
                property.setStatus(status);

                // Set optional fields
                if (bedroomsStr != null && !bedroomsStr.trim().isEmpty()) {
                    property.setBedrooms(Integer.parseInt(bedroomsStr));
                } else {
                    property.setBedrooms(null);
                }

                if (bathroomsStr != null && !bathroomsStr.trim().isEmpty()) {
                    property.setBathrooms(Integer.parseInt(bathroomsStr));
                } else {
                    property.setBathrooms(null);
                }

                if (sizeStr != null && !sizeStr.trim().isEmpty()) {
                    property.setSize(new BigDecimal(sizeStr));
                } else {
                    property.setSize(null);
                }

                if (sizeUnit != null && !sizeUnit.trim().isEmpty()) {
                    property.setSizeUnit(sizeUnit);
                } else {
                    property.setSizeUnit("sq.ft");
                }

                if (imagePath != null && !imagePath.trim().isEmpty()) {
                    property.setPrimaryImagePath(imagePath);
                }

                boolean success = false;
                if ("add".equals(action)) {
                    // Save new property to database
                    Property savedProperty = propertyService.createProperty(property);
                    success = (savedProperty != null);

                    if (success) {
                        session.setAttribute("successMessage", "Property added successfully");
                    } else {
                        request.setAttribute("errorMessage", "Failed to add property");
                        request.setAttribute("user", user);
                        request.setAttribute("activeTab", "properties");
                        request.getRequestDispatcher("/admin-add-property.jsp").forward(request, response);
                        return;
                    }
                } else {
                    // Update existing property
                    success = propertyService.updateProperty(property);

                    if (success) {
                        session.setAttribute("successMessage", "Property updated successfully");
                    } else {
                        request.setAttribute("errorMessage", "Failed to update property");
                        request.setAttribute("user", user);
                        request.setAttribute("property", property);
                        request.setAttribute("activeTab", "properties");
                        request.getRequestDispatcher("/admin-edit-property.jsp").forward(request, response);
                        return;
                    }
                }

                // Redirect back to properties list
                response.sendRedirect(request.getContextPath() + "/admin/properties");

            } catch (NumberFormatException e) {
                String errorPage = "add".equals(action) ? "/admin-add-property.jsp" : "/admin-edit-property.jsp";
                request.setAttribute("errorMessage", "Invalid number format. Please check your inputs.");
                request.setAttribute("user", user);
                request.setAttribute("activeTab", "properties");

                if ("update".equals(action)) {
                    String propertyIdStr = request.getParameter("propertyId");
                    if (propertyIdStr != null && !propertyIdStr.trim().isEmpty()) {
                        int propertyId = Integer.parseInt(propertyIdStr);
                        Property property = propertyService.getPropertyById(propertyId);
                        request.setAttribute("property", property);
                    }
                }

                request.getRequestDispatcher(errorPage).forward(request, response);
            } catch (Exception e) {
                String errorPage = "add".equals(action) ? "/admin-add-property.jsp" : "/admin-edit-property.jsp";
                request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
                request.setAttribute("user", user);
                request.setAttribute("activeTab", "properties");

                if ("update".equals(action)) {
                    String propertyIdStr = request.getParameter("propertyId");
                    if (propertyIdStr != null && !propertyIdStr.trim().isEmpty()) {
                        int propertyId = Integer.parseInt(propertyIdStr);
                        Property property = propertyService.getPropertyById(propertyId);
                        request.setAttribute("property", property);
                    }
                }

                request.getRequestDispatcher(errorPage).forward(request, response);
            }
            return;
        }

        // Default: redirect back to admin properties page
        response.sendRedirect(request.getContextPath() + "/admin/properties");
    }
}
