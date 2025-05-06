package com.ghardalali.model;

import java.sql.Timestamp;

/**
 * SavedProperty model class representing the saved_properties table in the database
 */
public class SavedProperty {
    private int savedId;
    private int userId;
    private int propertyId;
    private Timestamp savedAt;
    private Property property; // Associated property object
    
    // Default constructor
    public SavedProperty() {
    }
    
    // Constructor with essential fields
    public SavedProperty(int userId, int propertyId) {
        this.userId = userId;
        this.propertyId = propertyId;
    }
    
    // Full constructor
    public SavedProperty(int savedId, int userId, int propertyId, Timestamp savedAt) {
        this.savedId = savedId;
        this.userId = userId;
        this.propertyId = propertyId;
        this.savedAt = savedAt;
    }
    
    // Getters and Setters
    public int getSavedId() {
        return savedId;
    }
    
    public void setSavedId(int savedId) {
        this.savedId = savedId;
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
    
    public Timestamp getSavedAt() {
        return savedAt;
    }
    
    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    @Override
    public String toString() {
        return "SavedProperty [savedId=" + savedId + ", userId=" + userId + ", propertyId=" + propertyId + ", savedAt=" + savedAt + "]";
    }
}
