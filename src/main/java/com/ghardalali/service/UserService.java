package com.ghardalali.service;

import java.util.Date;
import java.util.List;

import com.ghardalali.model.User;
import com.ghardalali.model.UserDAO;
import com.ghardalali.util.EmailUtil;
import com.ghardalali.util.PasswordUtil;
import com.ghardalali.util.TokenUtil;
import com.ghardalali.util.ValidationUtil;

/**
 * Service class for user-related operations
 */
public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Register a new user
     *
     * @param firstName     User's first name
     * @param lastName      User's last name
     * @param email         User's email
     * @param password      User's password (plain text)
     * @param contactNumber User's contact number
     * @param address       User's address
     * @return User ID if registration successful, -1 otherwise
     */
    public int registerUser(String firstName, String lastName, String email, String password, String contactNumber,
            String address) {
        try {
            // Check if email already exists
            if (userDAO.isEmailExists(email)) {
                System.out.println("Email already exists: " + email);
                return -1;
            }

            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Create user object
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setContactNumber(contactNumber);
            user.setAddress(address);

            // Log registration attempt (without password)
            System.out.println("Attempting to register user: " + firstName + " " + lastName + " (" + email + ")");

            // Register the user
            int userId = userDAO.registerUser(user);

            if (userId > 0) {
                System.out.println("User registered successfully with ID: " + userId);
            } else {
                System.err.println("Failed to register user: " + email);
            }

            return userId;
        } catch (Exception e) {
            System.err.println("Exception during user registration: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Authenticate user login
     *
     * @param email    User's email
     * @param password User's password (plain text)
     * @return User object if authentication successful, null otherwise
     */
    public User loginUser(String email, String password) {
        try {
            // Log login attempt (without password)
            System.out.println("Login attempt for email: " + email);

            // Authenticate the user with plain text password
            // The DAO will handle password verification
            User user = userDAO.authenticateUser(email, password);

            if (user != null) {
                System.out.println("Login successful for user: " + email);
            } else {
                System.out.println("Login failed for user: " + email);
            }

            return user;
        } catch (Exception e) {
            System.err.println("Exception during login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get user by ID
     *
     * @param userId User ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    /**
     * Update user password
     *
     * @param userId          User ID
     * @param currentPassword Current password (plain text)
     * @param newPassword     New password (plain text)
     * @return true if update successful, false otherwise
     */
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        // Get user by ID
        User user = userDAO.getUserById(userId);

        if (user == null) {
            return false;
        }

        // Verify current password
        if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
            return false;
        }

        // Hash the new password
        String hashedNewPassword = PasswordUtil.hashPassword(newPassword);

        // Update the password
        return userDAO.updatePassword(userId, hashedNewPassword);
    }

    /**
     * Check if email already exists
     *
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        return userDAO.isEmailExists(email);
    }

    /**
     * Create a password reset token and send reset email
     *
     * @param email   User's email
     * @param baseUrl Base URL of the application
     * @return true if token created and email sent successfully, false otherwise
     */
    public boolean createPasswordResetTokenAndSendEmail(String email, String baseUrl) {
        // Check if email exists
        if (!userDAO.isEmailExists(email)) {
            return false;
        }

        // Generate token
        String token = TokenUtil.generateToken();

        // Calculate expiry date
        Date expiryDate = TokenUtil.calculateExpiryDate();

        // Create token in database
        boolean tokenCreated = userDAO.createPasswordResetToken(email, token, expiryDate);

        if (!tokenCreated) {
            return false;
        }

        // Generate reset URL
        String resetUrl = TokenUtil.generateResetUrl(baseUrl, token);

        // Send email
        return EmailUtil.sendPasswordResetEmail(email, resetUrl, TokenUtil.TOKEN_EXPIRY_HOURS);
    }

    /**
     * Validate a password reset token
     *
     * @param token Reset token
     * @return User's email if token is valid, null otherwise
     */
    public String validatePasswordResetToken(String token) {
        return userDAO.validatePasswordResetToken(token);
    }

    /**
     * Reset user password
     *
     * @param token       Reset token
     * @param newPassword New password (plain text)
     * @return true if password reset successful, false otherwise
     */
    public boolean resetPassword(String token, String newPassword) {
        // Validate token
        String email = userDAO.validatePasswordResetToken(token);

        if (email == null) {
            return false;
        }

        // Hash the new password
        String hashedPassword = PasswordUtil.hashPassword(newPassword);

        // Reset password
        boolean passwordReset = userDAO.resetPassword(email, hashedPassword);

        if (passwordReset) {
            // Delete the used token
            userDAO.deletePasswordResetToken(token);
            return true;
        }

        return false;
    }

    /**
     * Get all users
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Delete a user
     *
     * @param userId User ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    /**
     * Update user profile information
     *
     * @param userId        User ID
     * @param firstName     User's first name
     * @param lastName      User's last name
     * @param contactNumber User's contact number
     * @param address       User's address
     * @return true if update successful, false otherwise
     */
    public boolean updateUserProfile(int userId, String firstName, String lastName, String contactNumber,
            String address) {
        try {
            // Get user by ID
            User user = userDAO.getUserById(userId);

            if (user == null) {
                return false;
            }

            // Validate input
            boolean hasError = false;

            if (firstName == null || firstName.trim().isEmpty() || !ValidationUtil.isValidName(firstName)) {
                hasError = true;
            }

            if (lastName == null || lastName.trim().isEmpty() || !ValidationUtil.isValidName(lastName)) {
                hasError = true;
            }

            if (contactNumber != null && !contactNumber.trim().isEmpty()
                    && !ValidationUtil.isValidPhone(contactNumber)) {
                hasError = true;
            }

            if (hasError) {
                return false;
            }

            // Update user object
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setContactNumber(contactNumber);
            user.setAddress(address);

            // Update user in database
            return userDAO.updateUserProfile(user);
        } catch (Exception e) {
            System.err.println("Exception during profile update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update user profile image
     *
     * @param userId           User ID
     * @param profileImagePath Path to the profile image
     * @return true if update successful, false otherwise
     */
    public boolean updateProfileImage(int userId, String profileImagePath) {
        try {
            // Validate inputs
            if (userId <= 0 || profileImagePath == null || profileImagePath.trim().isEmpty()) {
                return false;
            }

            // Update profile image in database
            return userDAO.updateProfileImage(userId, profileImagePath);
        } catch (Exception e) {
            System.err.println("Exception during profile image update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
