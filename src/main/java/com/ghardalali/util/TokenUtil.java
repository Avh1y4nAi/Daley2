package com.ghardalali.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Utility class for generating and validating tokens
 */
public class TokenUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    // Token expiration time in hours
    public static final int TOKEN_EXPIRY_HOURS = 24;

    /**
     * Generate a secure random token for password reset
     *
     * @return Secure random token
     */
    public static String generateToken() {
        byte[] randomBytes = new byte[32];
        RANDOM.nextBytes(randomBytes);
        return ENCODER.encodeToString(randomBytes);
    }

    /**
     * Generate a UUID-based token
     *
     * @return UUID-based token
     */
    public static String generateUUIDToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Calculate token expiration date
     *
     * @return Expiration date
     */
    public static Date calculateExpiryDate() {
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS);
        return Date.from(expiryDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Check if a token is expired
     *
     * @param expiryDate Token expiry date
     * @return true if expired, false otherwise
     */
    public static boolean isTokenExpired(Date expiryDate) {
        if (expiryDate == null) {
            return true;
        }

        return expiryDate.before(new Date());
    }

    /**
     * Generate a complete reset URL
     *
     * @param baseUrl Base URL of the application
     * @param token   Reset token
     * @return Complete reset URL
     */
    public static String generateResetUrl(String baseUrl, String token) {
        return baseUrl + "/reset-password?token=" + token;
    }
}
