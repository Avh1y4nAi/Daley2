package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.service.SavedPropertyService;
import com.ghardalali.service.ApplicationService;
import com.ghardalali.service.ReviewService;

/**
 * Servlet for handling user dashboard
 */
@WebServlet("/dashboard")
public class DashboardServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private SavedPropertyService savedPropertyService;
    private ApplicationService applicationService;
    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        super.init();
        savedPropertyService = new SavedPropertyService();
        applicationService = new ApplicationService();
        reviewService = new ReviewService();
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

        // Get dashboard statistics for the user
        int savedPropertiesCount = savedPropertyService.countSavedProperties(user.getUserId());
        int applicationsCount = applicationService.countUserApplications(user.getUserId());
        int reviewsCount = reviewService.countUserReviews(user.getUserId());

        // Set statistics in request attributes
        request.setAttribute("savedPropertiesCount", savedPropertiesCount);
        request.setAttribute("applicationsCount", applicationsCount);
        request.setAttribute("reviewsCount", reviewsCount);

        // Set user in request attribute
        request.setAttribute("user", user);

        // Set active tab to "dashboard" for proper navigation highlighting
        request.setAttribute("activeTab", "dashboard");

        // Forward to dashboard page
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
