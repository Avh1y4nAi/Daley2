package com.ghardalali.service;

import java.util.List;

import com.ghardalali.model.SavedProperty;
import com.ghardalali.model.SavedPropertyDAO;

/**
 * Service class for saved properties business logic
 */
public class SavedPropertyService {
    private SavedPropertyDAO savedPropertyDAO;
    
    public SavedPropertyService() {
        this.savedPropertyDAO = new SavedPropertyDAO();
    }
    
    /**
     * Save a property for a user
     * 
     * @param userId The ID of the user
     * @param propertyId The ID of the property to save
     * @return true if saved successfully, false otherwise
     */
    public boolean saveProperty(int userId, int propertyId) {
        // Check if property is already saved
        if (savedPropertyDAO.isPropertySaved(userId, propertyId)) {
            return true; // Already saved, consider it a success
        }
        
        return savedPropertyDAO.saveProperty(userId, propertyId);
    }
    
    /**
     * Remove a saved property for a user
     * 
     * @param userId The ID of the user
     * @param propertyId The ID of the property to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeSavedProperty(int userId, int propertyId) {
        return savedPropertyDAO.removeSavedProperty(userId, propertyId);
    }
    
    /**
     * Check if a property is saved by a user
     * 
     * @param userId The ID of the user
     * @param propertyId The ID of the property
     * @return true if property is saved by the user, false otherwise
     */
    public boolean isPropertySaved(int userId, int propertyId) {
        return savedPropertyDAO.isPropertySaved(userId, propertyId);
    }
    
    /**
     * Get all saved properties for a user with property details
     * 
     * @param userId The ID of the user
     * @return List of SavedProperty objects with property details
     */
    public List<SavedProperty> getSavedPropertiesWithDetails(int userId) {
        return savedPropertyDAO.getSavedPropertiesWithDetails(userId);
    }
    
    /**
     * Count the number of saved properties for a user
     * 
     * @param userId The ID of the user
     * @return The count of saved properties
     */
    public int countSavedProperties(int userId) {
        return savedPropertyDAO.countSavedProperties(userId);
    }
}
