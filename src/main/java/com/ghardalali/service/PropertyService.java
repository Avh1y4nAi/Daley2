package com.ghardalali.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ghardalali.model.Property;
import com.ghardalali.model.PropertyDAO;

/**
 * Service class for property-related operations
 */
public class PropertyService {
    private PropertyDAO propertyDAO;

    public PropertyService() {
        this.propertyDAO = new PropertyDAO();
    }

    /**
     * Get all properties
     *
     * @return List of Property objects
     */
    public List<Property> getAllProperties() {
        return propertyDAO.getAllProperties();
    }

    /**
     * Get property by ID
     *
     * @param propertyId Property ID
     * @return Property object if found, null otherwise
     */
    public Property getPropertyById(int propertyId) {
        return propertyDAO.getPropertyById(propertyId);
    }

    /**
     * Search properties based on criteria
     *
     * @param keyword      Search keyword
     * @param propertyType Property type
     * @param minPrice     Minimum price
     * @param maxPrice     Maximum price
     * @param bedrooms     Minimum number of bedrooms
     * @param bathrooms    Minimum number of bathrooms
     * @param status       Property status
     * @param sortBy       Sort criteria
     * @return List of Property objects matching the criteria
     */
    public List<Property> searchProperties(String keyword, String propertyType, Double minPrice, Double maxPrice,
            Integer bedrooms, Integer bathrooms, String status, String sortBy) {
        Map<String, Object> searchCriteria = new HashMap<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            searchCriteria.put("keyword", keyword);
        }

        if (propertyType != null && !propertyType.trim().isEmpty() && !propertyType.equals("All Types")) {
            searchCriteria.put("propertyType", propertyType);
        }

        if (minPrice != null) {
            searchCriteria.put("minPrice", minPrice);
        }

        if (maxPrice != null) {
            searchCriteria.put("maxPrice", maxPrice);
        }

        if (bedrooms != null) {
            searchCriteria.put("bedrooms", bedrooms);
        }

        if (bathrooms != null) {
            searchCriteria.put("bathrooms", bathrooms);
        }

        if (status != null && !status.trim().isEmpty() && !status.equals("All")) {
            searchCriteria.put("status", status);
        }

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            searchCriteria.put("sortBy", sortBy);
        }

        return propertyDAO.searchProperties(searchCriteria);
    }

    /**
     * Get featured properties
     *
     * @param limit Number of properties to return
     * @return List of featured Property objects
     */
    public List<Property> getFeaturedProperties(int limit) {
        return propertyDAO.getFeaturedProperties(limit);
    }

    /**
     * Get similar properties
     *
     * @param propertyId Property ID to find similar properties for
     * @param limit      Number of properties to return
     * @return List of similar Property objects
     */
    public List<Property> getSimilarProperties(int propertyId, int limit) {
        return propertyDAO.getSimilarProperties(propertyId, limit);
    }

    /**
     * Delete a property
     *
     * @param propertyId Property ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteProperty(int propertyId) {
        return propertyDAO.deleteProperty(propertyId);
    }

    /**
     * Create a new property
     *
     * @param property Property object to create
     * @return The created Property object with ID, or null if creation failed
     */
    public Property createProperty(Property property) {
        return propertyDAO.createProperty(property);
    }

    /**
     * Add an image to a property
     *
     * @param propertyId Property ID
     * @param imagePath  Path to the image
     * @param isPrimary  Whether this is the primary image
     * @return true if successful, false otherwise
     */
    public boolean addPropertyImage(int propertyId, String imagePath, boolean isPrimary) {
        return propertyDAO.addPropertyImage(propertyId, imagePath, isPrimary, null);
    }

    /**
     * Update an existing property
     *
     * @param property Property object with updated values
     * @return true if update successful, false otherwise
     */
    public boolean updateProperty(Property property) {
        return propertyDAO.updateProperty(property);
    }
}
