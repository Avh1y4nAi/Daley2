package com.ghardalali.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.service.UserService;
import com.ghardalali.util.SessionUtil;
import com.ghardalali.util.ValidationUtil;

/**
 * Servlet for handling user login
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        if (SessionUtil.isLoggedIn(request)) {
            // User is already logged in, redirect based on role
            User user = SessionUtil.getCurrentUser(request);
            if (user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/profile");
            }
            return;
        }

        // Check for "remember me" cookie
        String rememberedEmail = SessionUtil.getRememberedEmail(request);
        if (rememberedEmail != null) {
            request.setAttribute("rememberedEmail", rememberedEmail);
        }

        // Generate CSRF token for the form
        HttpSession session = request.getSession(true);
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Forward to login page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // Map to track login attempts by IP
    private static final Map<String, Integer> loginAttempts = new HashMap<>();
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final int LOGIN_LOCKOUT_TIME = 15 * 60 * 1000; // 15 minutes in milliseconds
    private static final Map<String, Long> lockedIPs = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            request.setAttribute("errorMessage", "Invalid request. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Get client IP address
        String clientIP = request.getRemoteAddr();

        // Check if IP is locked out
        if (isIPLocked(clientIP)) {
            request.setAttribute("errorMessage", "Too many failed login attempts. Please try again later.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Sanitize email input
        email = ValidationUtil.sanitizeInput(email);

        // Validate input
        boolean hasError = false;

        if (ValidationUtil.isNullOrEmpty(email)) {
            request.setAttribute("emailError", "Email is required");
            hasError = true;
        } else if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Please enter a valid email address");
            hasError = true;
        }

        if (ValidationUtil.isNullOrEmpty(password)) {
            request.setAttribute("passwordError", "Password is required");
            hasError = true;
        }

        if (hasError) {
            // Preserve email for convenience
            request.setAttribute("email", email);

            // Regenerate CSRF token
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            // Authenticate user
            User user = userService.loginUser(email, password);

            if (user != null) {
                // Reset login attempts for this IP
                resetLoginAttempts(clientIP);

                // Create user session
                boolean rememberMeFlag = (rememberMe != null && rememberMe.equals("on"));
                SessionUtil.createUserSession(request, response, user, rememberMeFlag);

                // Log successful login
                System.out.println("User logged in: " + email);

                // Redirect based on user role
                if (user.isAdmin()) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/profile");
                }
            } else {
                // Increment failed login attempts
                incrementLoginAttempts(clientIP);

                // Authentication failed
                request.setAttribute("errorMessage", "Invalid email or password");
                request.setAttribute("email", email); // Preserve email for convenience

                // Regenerate CSRF token
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            // Show a user-friendly error message
            request.setAttribute("errorMessage", "An error occurred during login. Please try again later.");
            request.setAttribute("email", email); // Preserve email for convenience

            // Regenerate CSRF token
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    /**
     * Check if an IP is locked out due to too many failed login attempts
     *
     * @param ip IP address to check
     * @return true if locked, false otherwise
     */
    private synchronized boolean isIPLocked(String ip) {
        if (!lockedIPs.containsKey(ip)) {
            return false;
        }

        long lockTime = lockedIPs.get(ip);
        long currentTime = System.currentTimeMillis();

        if (currentTime - lockTime > LOGIN_LOCKOUT_TIME) {
            // Lockout period has expired
            lockedIPs.remove(ip);
            loginAttempts.remove(ip);
            return false;
        }

        return true;
    }

    /**
     * Increment failed login attempts for an IP
     *
     * @param ip IP address
     */
    private synchronized void incrementLoginAttempts(String ip) {
        int attempts = loginAttempts.getOrDefault(ip, 0) + 1;
        loginAttempts.put(ip, attempts);

        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            // Lock the IP
            lockedIPs.put(ip, System.currentTimeMillis());
        }
    }

    /**
     * Reset login attempts for an IP after successful login
     *
     * @param ip IP address
     */
    private synchronized void resetLoginAttempts(String ip) {
        loginAttempts.remove(ip);
        lockedIPs.remove(ip);
    }
}
