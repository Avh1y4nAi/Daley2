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

/**
 * Servlet for handling user reviews in dashboard
 */
@WebServlet("/dashboard/reviews")
public class ReviewsServlet extends BaseServlet {
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

        // Get reviews for the user
        List<Review> reviews = reviewService.getReviewsByUserId(user.getUserId());

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("reviews", reviews);
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
            boolean success = reviewService.deleteReview(reviewId);

            if (success) {
                request.setAttribute("successMessage", "Review deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete review");
            }
        } else if ("edit".equals(action)) {
            // Edit review
            try {
                int reviewId = Integer.parseInt(request.getParameter("reviewId"));
                int rating = Integer.parseInt(request.getParameter("rating"));
                String reviewText = request.getParameter("reviewText");

                // Validate input
                if (rating < 1 || rating > 5) {
                    request.setAttribute("errorMessage", "Rating must be between 1 and 5");
                } else if (reviewText == null || reviewText.trim().isEmpty()) {
                    request.setAttribute("errorMessage", "Review text cannot be empty");
                } else {
                    // Get the review to verify ownership
                    Review review = reviewService.getReviewById(reviewId);

                    if (review != null && review.getUserId() == user.getUserId()) {
                        // Update review
                        boolean success = reviewService.updateReview(reviewId, rating, reviewText);

                        if (success) {
                            request.setAttribute("successMessage", "Review updated successfully");
                        } else {
                            request.setAttribute("errorMessage", "Failed to update review");
                        }
                    } else {
                        request.setAttribute("errorMessage", "You can only edit your own reviews");
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid review ID or rating");
            }
        }

        // Get updated reviews for the user
        List<Review> reviews = reviewService.getReviewsByUserId(user.getUserId());

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("reviews", reviews);
        request.setAttribute("activeTab", "reviews");

        // Forward to reviews page
        request.getRequestDispatcher("/reviews.jsp").forward(request, response);
    }
}
