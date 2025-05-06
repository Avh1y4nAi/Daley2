package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.Application;
import com.ghardalali.model.User;
import com.ghardalali.service.ApplicationService;

/**
 * Servlet for handling admin applications management
 */
@WebServlet("/admin/applications")
public class AdminApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ApplicationService applicationService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        applicationService = new ApplicationService();
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
        
        // Get all applications
        List<Application> applications = applicationService.getAllApplicationsWithDetails();
        
        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("applications", applications);
        
        // Forward to admin applications page
        request.getRequestDispatcher("/admin-applications.jsp").forward(request, response);
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
        
        if ("approve".equals(action)) {
            // Approve application
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            boolean success = applicationService.updateApplicationStatus(applicationId, "APPROVED");
            
            if (success) {
                request.setAttribute("successMessage", "Application approved successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to approve application");
            }
        } else if ("reject".equals(action)) {
            // Reject application
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            boolean success = applicationService.updateApplicationStatus(applicationId, "REJECTED");
            
            if (success) {
                request.setAttribute("successMessage", "Application rejected successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to reject application");
            }
        } else if ("delete".equals(action)) {
            // Delete application
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            boolean success = applicationService.deleteApplication(applicationId);
            
            if (success) {
                request.setAttribute("successMessage", "Application deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete application");
            }
        }
        
        // Redirect back to admin applications page
        response.sendRedirect(request.getContextPath() + "/admin/applications");
    }
}
