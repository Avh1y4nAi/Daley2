package com.ghardalali.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and verification
 */
public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    private static final String DELIMITER = ":";

    /**
     * Hash a password using SHA-256 algorithm with salt
     *
     * @param password Password to hash
     * @return Hashed password with salt (format: salt:hash)
     */
    public static String hashPassword(String password) {
        try {
            // Generate a random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Hash the password with the salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Convert salt and hash to Base64 for storage
            String saltStr = Base64.getEncoder().encodeToString(salt);
            String hashStr = Base64.getEncoder().encodeToString(hashedPassword);

            // Return salt:hash
            return saltStr + DELIMITER + hashStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    /**
     * Verify if a password matches its hash
     *
     * @param password   Plain text password
     * @param storedHash Stored hash with salt (format: salt:hash)
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split the stored hash into salt and hash
            String[] parts = storedHash.split(DELIMITER);
            if (parts.length != 2) {
                return false; // Invalid stored hash format
            }

            String saltStr = parts[0];
            String hashStr = parts[1];

            // Decode the salt and hash
            byte[] salt = Base64.getDecoder().decode(saltStr);
            byte[] hash = Base64.getDecoder().decode(hashStr);

            // Hash the input password with the same salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] inputHash = md.digest(password.getBytes());

            // Compare the hashes
            if (hash.length != inputHash.length) {
                return false;
            }

            // Time-constant comparison to prevent timing attacks
            int diff = 0;
            for (int i = 0; i < hash.length; i++) {
                diff |= hash[i] ^ inputHash[i];
            }

            return diff == 0;
        } catch (Exception e) {
            e.printStackTrace();
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
