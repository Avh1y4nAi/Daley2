package com.ghardalali.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ghardalali.util.DBUtil;

/**
 * Data Access Object for Review entity
 */
public class ReviewDAO {

    /**
     * Create a new review
     * 
     * @param review The Review object to create
     * @return The ID of the created review, or -1 if creation failed
     */
    public int createReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, property_id, rating, review_text) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, review.getUserId());
            pstmt.setInt(2, review.getPropertyId());
            pstmt.setInt(3, review.getRating());
            pstmt.setString(4, review.getReviewText());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                return -1;
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get a review by ID
     * 
     * @param reviewId The ID of the review
     * @return The Review object if found, null otherwise
     */
    public Review getReviewById(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToReview(rs);
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all reviews for a user
     * 
     * @param userId The ID of the user
     * @return List of Review objects
     */
    public List<Review> getReviewsByUserId(int userId) {
        String sql = "SELECT r.*, p.property_name FROM reviews r " +
                     "JOIN properties p ON r.property_id = p.property_id " +
                     "WHERE r.user_id = ? " +
                     "ORDER BY r.reviewed_at DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                review.setPropertyTitle(rs.getString("property_name"));
                reviews.add(review);
            }

            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
            return reviews;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get all reviews with user and property details
     * 
     * @return List of Review objects with user and property details
     */
    public List<Review> getAllReviewsWithDetails() {
        String sql = "SELECT r.*, u.first_name, u.last_name, p.property_name FROM reviews r " +
                     "JOIN users u ON r.user_id = u.user_id " +
                     "JOIN properties p ON r.property_id = p.property_id " +
                     "ORDER BY r.reviewed_at DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Review> reviews = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Review review = mapResultSetToReview(rs);
                review.setUserName(rs.getString("first_name") + " " + rs.getString("last_name"));
                review.setPropertyTitle(rs.getString("property_name"));
                reviews.add(review);
            }

            return reviews;
        } catch (SQLException e) {
            e.printStackTrace();
            return reviews;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update a review
     * 
     * @param review The Review object with updated values
     * @return true if update successful, false otherwise
     */
    public boolean updateReview(Review review) {
        String sql = "UPDATE reviews SET rating = ?, review_text = ? WHERE review_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, review.getRating());
            pstmt.setString(2, review.getReviewText());
            pstmt.setInt(3, review.getReviewId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Delete a review
     * 
     * @param reviewId The ID of the review to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reviewId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Count the total number of reviews
     * 
     * @return The count of reviews
     */
    public int countTotalReviews() {
        String sql = "SELECT COUNT(*) FROM reviews";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Count the number of reviews for a user
     * 
     * @param userId The ID of the user
     * @return The count of reviews
     */
    public int countUserReviews(int userId) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE user_id = ?";

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
     * Map a ResultSet to a Review object
     * 
     * @param rs The ResultSet to map
     * @return The mapped Review object
     * @throws SQLException If a database access error occurs
     */
    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setPropertyId(rs.getInt("property_id"));
        review.setRating(rs.getInt("rating"));
        review.setReviewText(rs.getString("review_text"));
        review.setReviewedAt(rs.getTimestamp("reviewed_at"));
        review.setUpdatedAt(rs.getTimestamp("updated_at"));
        return review;
    }
}
