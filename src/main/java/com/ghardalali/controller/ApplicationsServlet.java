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
 * Servlet for handling applications in user dashboard
 */
@WebServlet("/dashboard/applications")
public class ApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ApplicationService applicationService;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationService = new ApplicationService();
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

        // Get applications for the user
        List<Application> applications = applicationService.getUserApplicationsWithDetails(user.getUserId());

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("applications", applications);
        request.setAttribute("activeTab", "applications");

        // Forward to applications page
        request.getRequestDispatcher("/applications.jsp").forward(request, response);
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

        if ("apply".equals(action)) {
            // Apply for property
            int propertyId = Integer.parseInt(request.getParameter("propertyId"));
            Application application = applicationService.createApplication(user.getUserId(), propertyId);

            // Redirect back to property detail page or properties page
            String referer = request.getParameter("referer");
            if (referer != null && !referer.isEmpty()) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/property-detail?id=" + propertyId);
            }

        } else if ("cancel".equals(action)) {
            // Cancel application
            int applicationId = Integer.parseInt(request.getParameter("applicationId"));
            boolean success = applicationService.deleteApplication(applicationId);

            // Redirect back to applications page
            response.sendRedirect(request.getContextPath() + "/dashboard/applications");
        } else {
            // Invalid action, redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }
}
