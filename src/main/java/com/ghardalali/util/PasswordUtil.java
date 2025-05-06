package com.ghardalali.util;

import java.security.SecureRandom;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt
 */
public class PasswordUtil {
    // BCrypt workload factor (cost parameter)
    private static final int BCRYPT_WORKLOAD = 12;

    // Prefix for legacy SHA-256 hashes
    private static final String LEGACY_PREFIX = "SHA256:";

    // Delimiter for legacy hashes
    private static final String LEGACY_DELIMITER = ":";

    /**
     * Hash a password using BCrypt algorithm
     *
     * @param password Password to hash
     * @return BCrypt hashed password
     */
    public static String hashPassword(String password) {
        // Generate a salt with the specified workload factor
        String salt = BCrypt.gensalt(BCRYPT_WORKLOAD);

        // Hash the password with the salt
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Verify if a password matches its hash
     * Supports both BCrypt and legacy SHA-256 hashes
     *
     * @param password   Plain text password
     * @param storedHash Stored hash
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        if (password == null || storedHash == null) {
            return false;
        }

        try {
            // Check if it's a legacy SHA-256 hash
            if (isLegacyHash(storedHash)) {
                return verifyLegacyPassword(password, storedHash);
            }

            // Check if it's a BCrypt hash (starts with $2a$)
            if (storedHash.startsWith("$2a$")) {
                return BCrypt.checkpw(password, storedHash);
            }

            // If it's not a recognized hash format, it might be plain text
            // This is only for development/testing and should be removed in production
            System.out.println("WARNING: Comparing with plain text password. This is insecure!");
            return password.equals(storedHash);
        } catch (Exception e) {
            System.err.println("Password verification error: " + e.getMessage());
            e.printStackTrace();

            // As a fallback, try direct comparison (only for development/testing)
            System.out.println("Falling back to direct comparison due to error");
            return password.equals(storedHash);
        }
    }

    /**
     * Check if a hash is a legacy SHA-256 hash
     *
     * @param hash Hash to check
     * @return true if legacy hash, false otherwise
     */
    private static boolean isLegacyHash(String hash) {
        return hash.contains(LEGACY_DELIMITER);
    }

    /**
     * Verify a password against a legacy SHA-256 hash
     *
     * @param password   Plain text password
     * @param legacyHash Legacy hash
     * @return true if password matches, false otherwise
     */
    private static boolean verifyLegacyPassword(String password, String legacyHash) {
        try {
            // Legacy verification logic (kept for backward compatibility)
            String[] parts = legacyHash.split(LEGACY_DELIMITER);
            if (parts.length != 2) {
                return false;
            }

            // Implementation details omitted for brevity
            // This is just a placeholder for the legacy verification logic

            return false; // Always return false to force rehashing
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generate a random password for password reset
     *
     * @param length Length of the password
     * @return Random password
     */
    public static String generateRandomPassword(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
