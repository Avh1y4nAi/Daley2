package com.ghardalali.model;

import java.sql.Timestamp;

/**
 * Review model class representing the reviews table in the database
 */
public class Review {
    private int reviewId;
    private int userId;
    private int propertyId;
    private int rating;
    private String reviewText;
    private Timestamp reviewedAt;
    private Timestamp updatedAt;
    
    // Additional fields for display purposes
    private String userName;
    private String propertyTitle;
    
    // Default constructor
    public Review() {
    }
    
    // Constructor with essential fields
    public Review(int userId, int propertyId, int rating, String reviewText) {
        this.userId = userId;
        this.propertyId = propertyId;
        this.rating = rating;
        this.reviewText = reviewText;
    }
    
    // Full constructor
    public Review(int reviewId, int userId, int propertyId, int rating, String reviewText, 
            Timestamp reviewedAt, Timestamp updatedAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.propertyId = propertyId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewedAt = reviewedAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getReviewText() {
        return reviewText;
    }
    
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    
    public Timestamp getReviewedAt() {
        return reviewedAt;
    }
    
    public void setReviewedAt(Timestamp reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPropertyTitle() {
        return propertyTitle;
    }
    
    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }
    
    @Override
    public String toString() {
        return "Review [reviewId=" + reviewId + 
               ", userId=" + userId + 
               ", propertyId=" + propertyId + 
               ", rating=" + rating + 
               ", reviewText=" + reviewText + 
               ", reviewedAt=" + reviewedAt + 
               ", updatedAt=" + updatedAt + "]";
    }
}
