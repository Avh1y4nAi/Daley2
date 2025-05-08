package com.ghardalali.service;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * Gets the main image for a property
     * 
     * @param propertyId The property ID
     * @return The URL of the main image
     */
    public String getMainImage(int propertyId) {
        Property property = getPropertyById(propertyId);
        if (property != null && property.getPrimaryImagePath() != null) {
            return property.getPrimaryImagePath();
        } else if (property != null && property.getImagePaths() != null && !property.getImagePaths().isEmpty()) {
            return property.getImagePaths().get(0);
        }
        return "/images/properties/default.jpg"; // Default image
    }

    /**
     * Gets all images for a property
     * 
     * @param propertyId The property ID
     * @return List of image URLs
     */
    public List<String> getAllImages(int propertyId) {
        Property property = getPropertyById(propertyId);
        if (property != null && property.getImagePaths() != null && !property.getImagePaths().isEmpty()) {
            return property.getImagePaths();
        }
        return Collections.singletonList("/images/properties/default.jpg"); // Default image
    }

    /**
     * Sets the primary image for a property
     * 
     * @param propertyId The property ID
     * @param imagePath  The image path to set as primary
     * @return true if successful, false otherwise
     */
    public boolean setPrimaryImage(int propertyId, String imagePath) {
        Property property = getPropertyById(propertyId);
        if (property != null) {
            // Check if the image exists in the property's images
            if (property.getImagePaths().contains(imagePath)) {
                // Update the primary image in the database
                boolean success = propertyDAO.updatePrimaryImage(propertyId, imagePath);
                if (success) {
                    property.setPrimaryImagePath(imagePath);
                }
                return success;
            }
        }
        return false;
    }

    /**
     * Gets the property gallery data for display
     * 
     * @param propertyId The property ID
     * @return Map containing mainImage and allImages
     */
    public Map<String, Object> getPropertyGalleryData(int propertyId) {
        Map<String, Object> galleryData = new HashMap<>();

        String mainImage = getMainImage(propertyId);
        List<String> allImages = getAllImages(propertyId);

        galleryData.put("mainImage", mainImage);
        galleryData.put("allImages", allImages);

        return galleryData;
    }
}
