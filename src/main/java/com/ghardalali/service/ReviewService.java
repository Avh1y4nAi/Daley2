package com.ghardalali.service;

import java.util.List;

import com.ghardalali.model.Review;
import com.ghardalali.model.ReviewDAO;

/**
 * Service class for review-related operations
 */
public class ReviewService {
    private ReviewDAO reviewDAO;
    
    public ReviewService() {
        this.reviewDAO = new ReviewDAO();
    }
    
    /**
     * Create a new review
     * 
     * @param userId The ID of the user
     * @param propertyId The ID of the property
     * @param rating The rating (1-5)
     * @param reviewText The review text
     * @return The ID of the created review, or -1 if creation failed
     */
    public int createReview(int userId, int propertyId, int rating, String reviewText) {
        Review review = new Review(userId, propertyId, rating, reviewText);
        return reviewDAO.createReview(review);
    }
    
    /**
     * Get a review by ID
     * 
     * @param reviewId The ID of the review
     * @return The Review object if found, null otherwise
     */
    public Review getReviewById(int reviewId) {
        return reviewDAO.getReviewById(reviewId);
    }
    
    /**
     * Get all reviews for a user
     * 
     * @param userId The ID of the user
     * @return List of Review objects
     */
    public List<Review> getReviewsByUserId(int userId) {
        return reviewDAO.getReviewsByUserId(userId);
    }
    
    /**
     * Get all reviews with user and property details
     * 
     * @return List of Review objects with user and property details
     */
    public List<Review> getAllReviewsWithDetails() {
        return reviewDAO.getAllReviewsWithDetails();
    }
    
    /**
     * Update a review
     * 
     * @param reviewId The ID of the review
     * @param rating The updated rating
     * @param reviewText The updated review text
     * @return true if update successful, false otherwise
     */
    public boolean updateReview(int reviewId, int rating, String reviewText) {
        Review review = reviewDAO.getReviewById(reviewId);
        if (review == null) {
            return false;
        }
        
        review.setRating(rating);
        review.setReviewText(reviewText);
        
        return reviewDAO.updateReview(review);
    }
    
    /**
     * Delete a review
     * 
     * @param reviewId The ID of the review to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteReview(int reviewId) {
        return reviewDAO.deleteReview(reviewId);
    }
    
    /**
     * Count the total number of reviews
     * 
     * @return The count of reviews
     */
    public int countTotalReviews() {
        return reviewDAO.countTotalReviews();
    }
    
    /**
     * Count the number of reviews for a user
     * 
     * @param userId The ID of the user
     * @return The count of reviews
     */
    public int countUserReviews(int userId) {
        return reviewDAO.countUserReviews(userId);
    }
}
