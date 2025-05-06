package com.ghardalali.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ghardalali.util.DBUtil;

/**
 * Data Access Object for Property-related database operations
 */
public class PropertyDAO {

    /**
     * Get all properties
     *
     * @return List of Property objects
     */
    public List<Property> getAllProperties() {
        String sql = "SELECT * FROM properties";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Property property = mapResultSetToProperty(rs);
                loadPropertyImages(property, conn);
                properties.add(property);
            }

            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            return properties;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get property by ID
     *
     * @param propertyId Property ID
     * @return Property object if found, null otherwise
     */
    public Property getPropertyById(int propertyId) {
        String sql = "SELECT * FROM properties WHERE property_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, propertyId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Property property = mapResultSetToProperty(rs);
                loadPropertyImages(property, conn);
                return property;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Search properties based on criteria
     *
     * @param searchCriteria Map of search criteria
     * @return List of Property objects matching the criteria
     */
    public List<Property> searchProperties(Map<String, Object> searchCriteria) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM properties WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Add search criteria to SQL query
        if (searchCriteria.containsKey("keyword")) {
            String keyword = "%" + searchCriteria.get("keyword") + "%";
            sqlBuilder.append(" AND (property_name LIKE ? OR description LIKE ? OR location LIKE ?)");
            params.add(keyword);
            params.add(keyword);
            params.add(keyword);
        }

        if (searchCriteria.containsKey("propertyType")) {
            sqlBuilder.append(" AND property_type = ?");
            params.add(searchCriteria.get("propertyType"));
        }

        if (searchCriteria.containsKey("minPrice")) {
            sqlBuilder.append(" AND price >= ?");
            params.add(searchCriteria.get("minPrice"));
        }

        if (searchCriteria.containsKey("maxPrice")) {
            sqlBuilder.append(" AND price <= ?");
            params.add(searchCriteria.get("maxPrice"));
        }

        if (searchCriteria.containsKey("bedrooms")) {
            sqlBuilder.append(" AND bedrooms >= ?");
            params.add(searchCriteria.get("bedrooms"));
        }

        if (searchCriteria.containsKey("bathrooms")) {
            sqlBuilder.append(" AND bathrooms >= ?");
            params.add(searchCriteria.get("bathrooms"));
        }

        if (searchCriteria.containsKey("status")) {
            sqlBuilder.append(" AND status = ?");
            params.add(searchCriteria.get("status"));
        }

        // Add sorting
        if (searchCriteria.containsKey("sortBy")) {
            String sortBy = (String) searchCriteria.get("sortBy");
            switch (sortBy) {
                case "price-asc":
                    sqlBuilder.append(" ORDER BY price ASC");
                    break;
                case "price-desc":
                    sqlBuilder.append(" ORDER BY price DESC");
                    break;
                case "newest":
                    sqlBuilder.append(" ORDER BY created_at DESC");
                    break;
                default:
                    sqlBuilder.append(" ORDER BY created_at DESC");
                    break;
            }
        } else {
            sqlBuilder.append(" ORDER BY created_at DESC");
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sqlBuilder.toString());

            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Property property = mapResultSetToProperty(rs);
                loadPropertyImages(property, conn);
                properties.add(property);
            }

            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            return properties;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get featured properties
     *
     * @param limit Number of properties to return
     * @return List of featured Property objects
     */
    public List<Property> getFeaturedProperties(int limit) {
        String sql = "SELECT * FROM properties ORDER BY created_at DESC LIMIT ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, limit);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Property property = mapResultSetToProperty(rs);
                loadPropertyImages(property, conn);
                properties.add(property);
            }

            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            return properties;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get similar properties
     *
     * @param propertyId Property ID to find similar properties for
     * @param limit      Number of properties to return
     * @return List of similar Property objects
     */
    public List<Property> getSimilarProperties(int propertyId, int limit) {
        String sql = "SELECT p.* FROM properties p " +
                "JOIN properties ref ON p.property_type = ref.property_type " +
                "WHERE ref.property_id = ? AND p.property_id != ? " +
                "ORDER BY ABS(p.price - ref.price) LIMIT ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, propertyId);
            pstmt.setInt(2, propertyId);
            pstmt.setInt(3, limit);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Property property = mapResultSetToProperty(rs);
                loadPropertyImages(property, conn);
                properties.add(property);
            }

            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            return properties;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Map ResultSet to Property object
     *
     * @param rs ResultSet
     * @return Property object
     * @throws SQLException if a database access error occurs
     */
    private Property mapResultSetToProperty(ResultSet rs) throws SQLException {
        Property property = new Property();
        property.setPropertyId(rs.getInt("property_id"));
        property.setPropertyName(rs.getString("property_name"));
        property.setPropertyType(rs.getString("property_type"));
        property.setDescription(rs.getString("description"));
        property.setLocation(rs.getString("location"));
        property.setPrice(rs.getBigDecimal("price"));
        property.setBedrooms(rs.getObject("bedrooms") != null ? rs.getInt("bedrooms") : null);
        property.setBathrooms(rs.getObject("bathrooms") != null ? rs.getInt("bathrooms") : null);
        property.setSize(rs.getBigDecimal("size"));
        property.setSizeUnit(rs.getString("size_unit"));
        property.setStatus(rs.getString("status"));
        property.setCreatedAt(rs.getTimestamp("created_at"));
        property.setUpdatedAt(rs.getTimestamp("updated_at"));

        // Set default values for additional properties
        property.setYearBuilt(2020);
        property.setGarage(1);
        property.setAddress("123 Mountain View Road, " + property.getLocation());
        property.setNeighborhood("Baluwatar");

        // Add default features
        property.addFeature("Air Conditioning");
        property.addFeature("Central Heating");
        property.addFeature("High-Speed Internet");
        property.addFeature("Modern Kitchen");
        property.addFeature("Garden");
        property.addFeature("Balcony");
        property.addFeature("Security System");
        property.addFeature("Parking Space");

        // Add default nearby places
        property.addNearbyPlace("International School (0.5 miles)");
        property.addNearbyPlace("Shopping Mall (1 mile)");
        property.addNearbyPlace("Hospital (1.2 miles)");
        property.addNearbyPlace("Park (0.3 miles)");
        property.addNearbyPlace("Bus Station (0.2 miles)");

        return property;
    }

    /**
     * Load property images
     *
     * @param property Property object
     * @param conn     Database connection
     * @throws SQLException if a database access error occurs
     */
    private void loadPropertyImages(Property property, Connection conn) throws SQLException {
        String sql = "SELECT * FROM property_images WHERE property_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, property.getPropertyId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String imagePath = rs.getString("image_path");
                property.addImagePath(imagePath);

                if (rs.getBoolean("is_primary")) {
                    property.setPrimaryImagePath(imagePath);
                }
            }

            // If no primary image is set, use the first image
            if (property.getPrimaryImagePath() == null && !property.getImagePaths().isEmpty()) {
                property.setPrimaryImagePath(property.getImagePaths().get(0));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        }
    }

    /**
     * Delete a property
     *
     * @param propertyId Property ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteProperty(int propertyId) {
        String sql = "DELETE FROM properties WHERE property_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, propertyId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
