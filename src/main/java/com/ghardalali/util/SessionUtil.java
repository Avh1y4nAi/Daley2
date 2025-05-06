package com.ghardalali.util;

import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;

/**
 * Utility class for session management
 */
public class SessionUtil {
    
    // Session attribute names
    public static final String USER_SESSION_ATTR = "user";
    public static final String CSRF_TOKEN_ATTR = "csrfToken";
    public static final String REMEMBER_ME_COOKIE = "rememberMe";
    public static final String EMAIL_COOKIE = "email";
    
    // Session timeout in seconds (30 minutes)
    public static final int SESSION_TIMEOUT = 30 * 60;
    
    // Remember me cookie expiration in seconds (30 days)
    public static final int REMEMBER_ME_EXPIRY = 30 * 24 * 60 * 60;
    
    /**
     * Creates a new session for a user
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param user User object
     * @param rememberMe Whether to set remember me cookie
     */
    public static void createUserSession(HttpServletRequest request, HttpServletResponse response, 
            User user, boolean rememberMe) {
        
        // Invalidate existing session if any
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        
        // Create new session
        HttpSession session = request.getSession(true);
        
        // Set session attributes
        session.setAttribute(USER_SESSION_ATTR, user);
        session.setAttribute(CSRF_TOKEN_ATTR, generateCSRFToken());
        
        // Set session timeout
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
        
        // Set remember me cookie if requested
        if (rememberMe) {
            setRememberMeCookie(response, user.getEmail());
        } else {
            // Delete any existing remember me cookie
            deleteRememberMeCookie(response);
        }
    }
    
    /**
     * Checks if a user is logged in
     * 
     * @param request HTTP request
     * @return true if user is logged in, false otherwise
     */
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(USER_SESSION_ATTR) != null;
    }
    
    /**
     * Gets the current logged in user
     * 
     * @param request HTTP request
     * @return User object if logged in, null otherwise
     */
    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        
        return (User) session.getAttribute(USER_SESSION_ATTR);
    }
    
    /**
     * Logs out a user by invalidating the session
     * 
     * @param request HTTP request
     * @param response HTTP response
     */
    public static void logoutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Delete remember me cookie
        deleteRememberMeCookie(response);
    }
    
    /**
     * Generates a CSRF token
     * 
     * @return CSRF token
     */
    public static String generateCSRFToken() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Validates a CSRF token
     * 
     * @param request HTTP request
     * @param token Token to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCSRFToken(HttpServletRequest request, String token) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        return sessionToken != null && sessionToken.equals(token);
    }
    
    /**
     * Sets a remember me cookie
     * 
     * @param response HTTP response
     * @param email User email
     */
    private static void setRememberMeCookie(HttpServletResponse response, String email) {
        Cookie emailCookie = new Cookie(EMAIL_COOKIE, email);
        emailCookie.setMaxAge(REMEMBER_ME_EXPIRY);
        emailCookie.setHttpOnly(true);
        emailCookie.setPath("/");
        emailCookie.setSecure(true); // Only send over HTTPS
        response.addCookie(emailCookie);
    }
    
    /**
     * Deletes the remember me cookie
     * 
     * @param response HTTP response
     */
    private static void deleteRememberMeCookie(HttpServletResponse response) {
        Cookie emailCookie = new Cookie(EMAIL_COOKIE, "");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);
    }
    
    /**
     * Gets the remembered email from cookie
     * 
     * @param request HTTP request
     * @return Email if found, null otherwise
     */
    public static String getRememberedEmail(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        
        for (Cookie cookie : cookies) {
            if (EMAIL_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        
        return null;
    }
}
