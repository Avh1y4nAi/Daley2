package com.ghardalali.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ghardalali.util.DBUtil;

/**
 * Data Access Object for Application entity
 */
public class ApplicationDAO {

    /**
     * Create a new application for a property
     *
     * @param userId     The ID of the user
     * @param propertyId The ID of the property
     * @return The created Application object with ID, or null if creation failed
     */
    public Application createApplication(int userId, int propertyId) {
        String sql = "INSERT INTO applications (user_id, property_id, status) VALUES (?, ?, 'PENDING')";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, propertyId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Application application = new Application(userId, propertyId);
                        application.setApplicationId(generatedKeys.getInt(1));
                        application.setStatus("PENDING");
                        return application;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Update the status of an application
     *
     * @param applicationId The ID of the application
     * @param status        The new status (PENDING, APPROVED, REJECTED)
     * @return true if updated successfully, false otherwise
     */
    public boolean updateApplicationStatus(int applicationId, String status) {
        String sql = "UPDATE applications SET status = ? WHERE application_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, applicationId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete an application
     *
     * @param applicationId The ID of the application to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteApplication(int applicationId) {
        String sql = "DELETE FROM applications WHERE application_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, applicationId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get an application by ID
     *
     * @param applicationId The ID of the application
     * @return The Application object, or null if not found
     */
    public Application getApplicationById(int applicationId) {
        String sql = "SELECT * FROM applications WHERE application_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, applicationId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Application application = new Application();
                    application.setApplicationId(rs.getInt("application_id"));
                    application.setUserId(rs.getInt("user_id"));
                    application.setPropertyId(rs.getInt("property_id"));
                    application.setStatus(rs.getString("status"));
                    application.setAppliedAt(rs.getTimestamp("applied_at"));
                    application.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return application;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Check if a user has already applied for a property
     *
     * @param userId     The ID of the user
     * @param propertyId The ID of the property
     * @return true if user has already applied, false otherwise
     */
    public boolean hasUserApplied(int userId, int propertyId) {
        String sql = "SELECT COUNT(*) FROM applications WHERE user_id = ? AND property_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, propertyId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get all applications for a user with property details
     *
     * @param userId The ID of the user
     * @return List of Application objects with property details
     */
    public List<Application> getUserApplicationsWithDetails(int userId) {
        List<Application> applications = new ArrayList<>();

        String sql = "SELECT a.*, p.* FROM applications a " +
                "JOIN properties p ON a.property_id = p.property_id " +
                "WHERE a.user_id = ? " +
                "ORDER BY a.applied_at DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application application = new Application();
                    application.setApplicationId(rs.getInt("application_id"));
                    application.setUserId(rs.getInt("user_id"));
                    application.setPropertyId(rs.getInt("property_id"));
                    application.setStatus(rs.getString("status"));
                    application.setAppliedAt(rs.getTimestamp("applied_at"));
                    application.setUpdatedAt(rs.getTimestamp("updated_at"));

                    // Create and set the property object
                    Property property = new Property();
                    property.setPropertyId(rs.getInt("property_id"));
                    property.setPropertyName(rs.getString("property_name"));
                    property.setPropertyType(rs.getString("property_type"));
                    property.setDescription(rs.getString("description"));
                    property.setLocation(rs.getString("location"));
                    property.setPrice(rs.getBigDecimal("price"));
                    property.setBedrooms(rs.getInt("bedrooms"));
                    property.setBathrooms(rs.getInt("bathrooms"));
                    property.setSize(rs.getBigDecimal("size"));
                    property.setSizeUnit(rs.getString("size_unit"));
                    property.setStatus(rs.getString("status"));

                    application.setProperty(property);
                    applications.add(application);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applications;
    }

    /**
     * Count the number of applications for a user
     *
     * @param userId The ID of the user
     * @return The count of applications
     */
    public int countUserApplications(int userId) {
        String sql = "SELECT COUNT(*) FROM applications WHERE user_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Get all applications with property and user details
     *
     * @return List of Application objects with property and user details
     */
    public List<Application> getAllApplicationsWithDetails() {
        List<Application> applications = new ArrayList<>();

        String sql = "SELECT a.*, p.*, u.first_name, u.last_name, u.email FROM applications a " +
                "JOIN properties p ON a.property_id = p.property_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "ORDER BY a.applied_at DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Application application = new Application();
                application.setApplicationId(rs.getInt("application_id"));
                application.setUserId(rs.getInt("user_id"));
                application.setPropertyId(rs.getInt("property_id"));
                application.setStatus(rs.getString("status"));
                application.setAppliedAt(rs.getTimestamp("applied_at"));
                application.setUpdatedAt(rs.getTimestamp("updated_at"));

                // Set user name for display
                application.setUserName(rs.getString("first_name") + " " + rs.getString("last_name"));
                application.setUserEmail(rs.getString("email"));

                // Create and set the property object
                Property property = new Property();
                property.setPropertyId(rs.getInt("property_id"));
                property.setPropertyName(rs.getString("property_name"));
                property.setPropertyType(rs.getString("property_type"));
                property.setDescription(rs.getString("description"));
                property.setLocation(rs.getString("location"));
                property.setPrice(rs.getBigDecimal("price"));
                property.setBedrooms(rs.getInt("bedrooms"));
                property.setBathrooms(rs.getInt("bathrooms"));
                property.setSize(rs.getBigDecimal("size"));
                property.setSizeUnit(rs.getString("size_unit"));
                property.setStatus(rs.getString("status"));

                application.setProperty(property);
                application.setPropertyTitle(property.getPropertyName());
                applications.add(application);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applications;
    }
}
