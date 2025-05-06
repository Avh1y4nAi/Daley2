package com.ghardalali.service;

import java.util.List;

import com.ghardalali.model.Application;
import com.ghardalali.model.ApplicationDAO;

/**
 * Service class for application business logic
 */
public class ApplicationService {
    private ApplicationDAO applicationDAO;

    public ApplicationService() {
        this.applicationDAO = new ApplicationDAO();
    }

    /**
     * Create a new application for a property
     *
     * @param userId     The ID of the user
     * @param propertyId The ID of the property
     * @return The created Application object with ID, or null if creation failed
     */
    public Application createApplication(int userId, int propertyId) {
        // Check if user has already applied
        if (applicationDAO.hasUserApplied(userId, propertyId)) {
            return null; // User has already applied
        }

        return applicationDAO.createApplication(userId, propertyId);
    }

    /**
     * Update the status of an application
     *
     * @param applicationId The ID of the application
     * @param status        The new status (PENDING, APPROVED, REJECTED)
     * @return true if updated successfully, false otherwise
     */
    public boolean updateApplicationStatus(int applicationId, String status) {
        // Validate status
        if (!"PENDING".equals(status) && !"APPROVED".equals(status) && !"REJECTED".equals(status)) {
            return false; // Invalid status
        }

        return applicationDAO.updateApplicationStatus(applicationId, status);
    }

    /**
     * Delete an application
     *
     * @param applicationId The ID of the application to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteApplication(int applicationId) {
        return applicationDAO.deleteApplication(applicationId);
    }

    /**
     * Get an application by ID
     *
     * @param applicationId The ID of the application
     * @return The Application object, or null if not found
     */
    public Application getApplicationById(int applicationId) {
        return applicationDAO.getApplicationById(applicationId);
    }

    /**
     * Check if a user has already applied for a property
     *
     * @param userId     The ID of the user
     * @param propertyId The ID of the property
     * @return true if user has already applied, false otherwise
     */
    public boolean hasUserApplied(int userId, int propertyId) {
        return applicationDAO.hasUserApplied(userId, propertyId);
    }

    /**
     * Get all applications for a user with property details
     *
     * @param userId The ID of the user
     * @return List of Application objects with property details
     */
    public List<Application> getUserApplicationsWithDetails(int userId) {
        return applicationDAO.getUserApplicationsWithDetails(userId);
    }

    /**
     * Count the number of applications for a user
     *
     * @param userId The ID of the user
     * @return The count of applications
     */
    public int countUserApplications(int userId) {
        return applicationDAO.countUserApplications(userId);
    }

    /**
     * Get all applications with property and user details
     *
     * @return List of Application objects with property and user details
     */
    public List<Application> getAllApplicationsWithDetails() {
        return applicationDAO.getAllApplicationsWithDetails();
    }
}
