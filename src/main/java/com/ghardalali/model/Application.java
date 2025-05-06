package com.ghardalali.model;

import java.sql.Timestamp;

/**
 * Application model class representing the applications table in the database
 */
public class Application {
    private int applicationId;
    private int userId;
    private int propertyId;
    private String status;
    private Timestamp appliedAt;
    private Timestamp updatedAt;
    private Property property; // Associated property object
    private String userName; // User's name for display
    private String userEmail; // User's email for display
    private String propertyTitle; // Property title for display

    // Default constructor
    public Application() {
    }

    // Constructor with essential fields
    public Application(int userId, int propertyId) {
        this.userId = userId;
        this.propertyId = propertyId;
        this.status = "PENDING";
    }

    // Full constructor
    public Application(int applicationId, int userId, int propertyId, String status, Timestamp appliedAt,
            Timestamp updatedAt) {
        this.applicationId = applicationId;
        this.userId = userId;
        this.propertyId = propertyId;
        this.status = status;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Timestamp appliedAt) {
        this.appliedAt = appliedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    @Override
    public String toString() {
        return "Application [applicationId=" + applicationId + ", userId=" + userId + ", propertyId=" + propertyId
                + ", status=" + status + ", appliedAt=" + appliedAt + ", updatedAt=" + updatedAt + "]";
    }
}
