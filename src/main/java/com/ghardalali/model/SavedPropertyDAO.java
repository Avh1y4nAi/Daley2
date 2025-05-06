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
 * Data Access Object for SavedProperty entity
 */
public class SavedPropertyDAO {

    /**
     * Save a property for a user
     * 
     * @param userId     The ID of the user
     * @param propertyId The ID of the property to save
     * @return true if saved successfully, false otherwise
     */
    public boolean saveProperty(int userId, int propertyId) {
        String sql = "INSERT INTO saved_properties (user_id, property_id) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, propertyId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove a saved property for a user
     * 
     * @param userId     The ID of the user
     * @param propertyId The ID of the property to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeSavedProperty(int userId, int propertyId) {
        String sql = "DELETE FROM saved_properties WHERE user_id = ? AND property_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, propertyId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if a property is saved by a user
     * 
     * @param userId     The ID of the user
     * @param propertyId The ID of the property
     * @return true if property is saved by the user, false otherwise
     */
    public boolean isPropertySaved(int userId, int propertyId) {
        String sql = "SELECT COUNT(*) FROM saved_properties WHERE user_id = ? AND property_id = ?";

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
     * Get all saved properties for a user with property details
     * 
     * @param userId The ID of the user
     * @return List of SavedProperty objects with property details
     */
    public List<SavedProperty> getSavedPropertiesWithDetails(int userId) {
        List<SavedProperty> savedProperties = new ArrayList<>();

        String sql = "SELECT sp.*, p.* FROM saved_properties sp " +
                "JOIN properties p ON sp.property_id = p.property_id " +
                "WHERE sp.user_id = ? " +
                "ORDER BY sp.saved_at DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SavedProperty savedProperty = new SavedProperty();
                    savedProperty.setSavedId(rs.getInt("saved_id"));
                    savedProperty.setUserId(rs.getInt("user_id"));
                    savedProperty.setPropertyId(rs.getInt("property_id"));
                    savedProperty.setSavedAt(rs.getTimestamp("saved_at"));

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

                    savedProperty.setProperty(property);
                    savedProperties.add(savedProperty);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return savedProperties;
    }

    /**
     * Count the number of saved properties for a user
     * 
     * @param userId The ID of the user
     * @return The count of saved properties
     */
    public int countSavedProperties(int userId) {
        String sql = "SELECT COUNT(*) FROM saved_properties WHERE user_id = ?";

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
}
