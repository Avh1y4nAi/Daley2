package com.ghardalali.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation and sanitization
 */
public class ValidationUtil {
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    // Phone number validation pattern (10 digits)
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");
    
    // Name validation pattern (letters, spaces, hyphens, apostrophes)
    private static final Pattern NAME_PATTERN = 
        Pattern.compile("^[\\p{L} .'-]+$");
    
    // Password validation pattern (at least 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char)
    private static final Pattern STRONG_PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    
    // Simple password validation pattern (at least 8 chars)
    private static final Pattern BASIC_PASSWORD_PATTERN = 
        Pattern.compile("^.{8,}$");
    
    /**
     * Validates an email address
     * 
     * @param email Email address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates a phone number (10 digits)
     * 
     * @param phone Phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validates a name (letters, spaces, hyphens, apostrophes)
     * 
     * @param name Name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name).matches();
    }
    
    /**
     * Validates a password against strong requirements
     * (at least 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special char)
     * 
     * @param password Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return STRONG_PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Validates a password against basic requirements (at least 8 chars)
     * 
     * @param password Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isBasicPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return BASIC_PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Sanitizes input to prevent XSS attacks
     * 
     * @param input Input to sanitize
     * @return Sanitized input
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        
        // Replace potentially dangerous characters
        String sanitized = input
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
            .replace("/", "&#x2F;");
            
        return sanitized;
    }
    
    /**
     * Checks if a string is null or empty
     * 
     * @param str String to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Validates password strength and returns a score (0-4)
     * 
     * @param password Password to check
     * @return Strength score (0-4)
     */
    public static int getPasswordStrength(String password) {
        int score = 0;
        
        if (password == null || password.isEmpty()) {
            return score;
        }
        
        // Length check
        if (password.length() >= 8) {
            score++;
        }
        
        // Uppercase check
        if (Pattern.compile("[A-Z]").matcher(password).find()) {
            score++;
        }
        
        // Number check
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            score++;
        }
        
        // Special character check
        if (Pattern.compile("[^A-Za-z0-9]").matcher(password).find()) {
            score++;
        }
        
        return score;
    }
}
