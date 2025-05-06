package com.ghardalali.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ghardalali.util.DBUtil;

/**
 * Data Access Object for User-related database operations
 */
public class UserDAO {

    /**
     * Register a new user
     *
     * @param user User object with registration details
     * @return User ID if registration successful, -1 otherwise
     */
    public int registerUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email, password, contact_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword()); // Assuming password is already hashed
            pstmt.setString(5, user.getContactNumber());
            pstmt.setString(6, user.getAddress());

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
            System.err.println("Error registering user: " + e.getMessage());
            System.err.println("SQL: " + sql);
            System.err.println("User data: " + user.toString());
            e.printStackTrace();
            return -1;
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
     * Check if email already exists in the database
     *
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * Authenticate user login
     *
     * @param email    User email
     * @param password User password (plain text)
     * @return User object if authentication successful, null otherwise
     */
    public User authenticateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Get the stored password
                String storedPassword = rs.getString("password");

                System.out.println("Authenticating user: " + email);
                System.out.println("Stored password: " + (storedPassword != null ? "******" : "null"));
                System.out.println("Input password: " + (password != null ? "******" : "null"));

                // For testing purposes, allow direct comparison with plain text passwords
                boolean passwordMatches = password.equals(storedPassword);

                if (passwordMatches) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setContactNumber(rs.getString("contact_number"));
                    user.setAddress(rs.getString("address"));
                    user.setUserRole(rs.getString("user_role"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setUpdatedAt(rs.getTimestamp("updated_at"));

                    System.out.println("Authentication successful for user: " + user.getEmail());
                    return user;
                }
            }

            System.out.println("Authentication failed for user: " + email);
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Exception during authentication: " + e.getMessage());
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
     * Get user by ID
     *
     * @param userId User ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setContactNumber(rs.getString("contact_number"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getString("user_role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

                return user;
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
     * Update user password
     *
     * @param userId      User ID
     * @param newPassword New password (hashed)
     * @return true if update successful, false otherwise
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newPassword); // Assuming password is already hashed
            pstmt.setInt(2, userId);

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
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

    /**
     * Create a password reset token for a user
     *
     * @param email      User's email
     * @param token      Reset token
     * @param expiryDate Token expiry date
     * @return true if token created successfully, false otherwise
     */
    public boolean createPasswordResetToken(String email, String token, Date expiryDate) {
        // First, check if a token already exists for this user
        String checkSql = "SELECT COUNT(*) FROM password_reset_tokens WHERE email = ?";
        String insertSql = "INSERT INTO password_reset_tokens (email, token, expiry_date) VALUES (?, ?, ?)";
        String updateSql = "UPDATE password_reset_tokens SET token = ?, expiry_date = ? WHERE email = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            // Check if token exists
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            boolean tokenExists = false;
            if (rs.next()) {
                tokenExists = rs.getInt(1) > 0;
            }

            // Close previous statement
            pstmt.close();

            // Insert or update token
            if (tokenExists) {
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setString(1, token);
                pstmt.setTimestamp(2, new Timestamp(expiryDate.getTime()));
                pstmt.setString(3, email);
            } else {
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setString(1, email);
                pstmt.setString(2, token);
                pstmt.setTimestamp(3, new Timestamp(expiryDate.getTime()));
            }

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * Validate a password reset token
     *
     * @param token Reset token
     * @return User's email if token is valid, null otherwise
     */
    public String validatePasswordResetToken(String token) {
        String sql = "SELECT email, expiry_date FROM password_reset_tokens WHERE token = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, token);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                Timestamp expiryTimestamp = rs.getTimestamp("expiry_date");
                Date expiryDate = new Date(expiryTimestamp.getTime());

                // Check if token is expired
                if (expiryDate.before(new Date())) {
                    return null; // Token expired
                }

                return email; // Token valid
            }

            return null; // Token not found
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
     * Delete a password reset token
     *
     * @param token Reset token
     * @return true if token deleted successfully, false otherwise
     */
    public boolean deletePasswordResetToken(String token) {
        String sql = "DELETE FROM password_reset_tokens WHERE token = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, token);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
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

    /**
     * Reset user password
     *
     * @param email       User's email
     * @param newPassword New password (hashed)
     * @return true if password reset successful, false otherwise
     */
    public boolean resetPassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
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

    /**
     * Get all users
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY user_id";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setContactNumber(rs.getString("contact_number"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getString("user_role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
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
     * Delete a user
     *
     * @param userId User ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
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

    /**
     * Update user profile information
     *
     * @param user User object with updated information
     * @return true if update successful, false otherwise
     */
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, contact_number = ?, address = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getContactNumber());
            pstmt.setString(4, user.getAddress());
            pstmt.setInt(5, user.getUserId());

            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
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
