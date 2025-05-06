package com.ghardalali.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;

/**
 * Servlet for handling user reviews in dashboard
 */
@WebServlet("/dashboard/reviews")
public class ReviewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("reviews", new ArrayList<>()); // Empty list for now
        request.setAttribute("activeTab", "reviews");

        // Forward to reviews page
        request.getRequestDispatcher("/reviews.jsp").forward(request, response);
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

        if ("delete".equals(action)) {
            // Delete review
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            // Placeholder for review deletion
            boolean success = true;

            if (success) {
                request.setAttribute("successMessage", "Review deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete review");
            }
        }

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("reviews", new ArrayList<>()); // Empty list for now
        request.setAttribute("activeTab", "reviews");

        // Forward to reviews page
        request.getRequestDispatcher("/reviews.jsp").forward(request, response);
    }
}
