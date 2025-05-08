package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.Review;
import com.ghardalali.model.User;
import com.ghardalali.service.ReviewService;
import com.ghardalali.util.SessionUtil;

/**
 * Servlet for handling admin reviews management
 */
@WebServlet("/admin/reviews")
public class AdminReviewsServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        super.init();
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

        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Get all reviews with details
        List<Review> reviews = reviewService.getAllReviewsWithDetails();

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("reviews", reviews);
        request.setAttribute("activeTab", "reviews");

        // Forward to admin reviews page
        request.getRequestDispatcher("/admin-reviews.jsp").forward(request, response);
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
            response.sendRedirect(request.getContextPath() + "/admin/reviews");
            return;
        }

        // Get action parameter
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Delete review
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            boolean success = reviewService.deleteReview(reviewId);

            if (success) {
                session.setAttribute("successMessage", "Review deleted successfully");
            } else {
                session.setAttribute("errorMessage", "Failed to delete review");
            }
        } else if ("edit".equals(action)) {
            // Edit review
            try {
                int reviewId = Integer.parseInt(request.getParameter("reviewId"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                String reviewText = request.getParameter("reviewText");

                // Validate input
                if (rating < 1 || rating > 5) {
                    session.setAttribute("errorMessage", "Rating must be between 1 and 5");
                } else if (reviewText == null || reviewText.trim().isEmpty()) {
                    session.setAttribute("errorMessage", "Review text cannot be empty");
                } else {
                    // Update review
                    boolean success = reviewService.updateReview(reviewId, rating, reviewText);

                    if (success) {
                        session.setAttribute("successMessage", "Review updated successfully");
                    } else {
                        session.setAttribute("errorMessage", "Failed to update review");
                    }
                }
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid review ID or rating");
            }
        }

        // Redirect back to admin reviews page
        response.sendRedirect(request.getContextPath() + "/admin/reviews");
    }
}
