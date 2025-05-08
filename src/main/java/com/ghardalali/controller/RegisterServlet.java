package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.service.UserService;
import com.ghardalali.util.SessionUtil;
import com.ghardalali.util.ValidationUtil;

/**
 * Servlet for handling user registration
 */
@WebServlet("/register")
public class RegisterServlet extends BaseServlet {
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
            // User is already logged in, redirect to profile
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        // Generate CSRF token for the form
        HttpSession session = request.getSession(true);
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Forward to registration page
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            request.setAttribute("errorMessage", "Invalid request. Please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contactNumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String termsAgreed = request.getParameter("termsAgreed");

        // Sanitize inputs to prevent XSS
        firstName = ValidationUtil.sanitizeInput(firstName);
        lastName = ValidationUtil.sanitizeInput(lastName);
        email = ValidationUtil.sanitizeInput(email);
        contactNumber = ValidationUtil.sanitizeInput(contactNumber);

        // Validate input
        boolean hasError = false;

        // First name validation
        if (ValidationUtil.isNullOrEmpty(firstName)) {
            request.setAttribute("firstNameError", "First name is required");
            hasError = true;
        } else if (firstName.length() > 50) {
            request.setAttribute("firstNameError", "First name cannot exceed 50 characters");
            hasError = true;
        } else if (!ValidationUtil.isValidName(firstName)) {
            request.setAttribute("firstNameError", "First name contains invalid characters");
            hasError = true;
        }

        // Last name validation
        if (ValidationUtil.isNullOrEmpty(lastName)) {
            request.setAttribute("lastNameError", "Last name is required");
            hasError = true;
        } else if (lastName.length() > 50) {
            request.setAttribute("lastNameError", "Last name cannot exceed 50 characters");
            hasError = true;
        } else if (!ValidationUtil.isValidName(lastName)) {
            request.setAttribute("lastNameError", "Last name contains invalid characters");
            hasError = true;
        }

        // Email validation
        if (ValidationUtil.isNullOrEmpty(email)) {
            request.setAttribute("emailError", "Email is required");
            hasError = true;
        } else if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Please enter a valid email address");
            hasError = true;
        } else if (userService.isEmailExists(email)) {
            request.setAttribute("emailError",
                    "This email is already registered. Please use a different email or login.");
            hasError = true;
        }

        // Contact number validation
        if (ValidationUtil.isNullOrEmpty(contactNumber)) {
            request.setAttribute("contactNumberError", "Contact number is required");
            hasError = true;
        } else if (!ValidationUtil.isValidPhone(contactNumber)) {
            request.setAttribute("contactNumberError", "Please enter a valid 10-digit contact number");
            hasError = true;
        }

        // Password validation
        if (ValidationUtil.isNullOrEmpty(password)) {
            request.setAttribute("passwordError", "Password is required");
            hasError = true;
        } else if (!ValidationUtil.isBasicPassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 8 characters long");
            hasError = true;
        } else {
            // Check password strength
            int passwordStrength = ValidationUtil.getPasswordStrength(password);
            if (passwordStrength < 3) {
                request.setAttribute("passwordError",
                        "Password is too weak. Include uppercase letters, numbers, and special characters.");
                hasError = true;
            }
        }

        // Confirm password validation
        if (ValidationUtil.isNullOrEmpty(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Please confirm your password");
            hasError = true;
        } else if (!confirmPassword.equals(password)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match");
            hasError = true;
        }

        // Terms agreement validation
        if (termsAgreed == null || !termsAgreed.equals("on")) {
            request.setAttribute("termsError", "You must agree to the terms and conditions");
            hasError = true;
        }

        if (hasError) {
            // Preserve input values (except password)
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("contactNumber", contactNumber);

            // Regenerate CSRF token
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

            // Forward back to registration page with errors
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            // Register user
            int userId = userService.registerUser(firstName, lastName, email, password, contactNumber, "");

            if (userId > 0) {
                // Log successful registration
                System.out.println("New user registered: " + email + " (ID: " + userId + ")");

                // Registration successful
                request.setAttribute("successMessage", "Registration successful! Please login with your new account.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                // Registration failed
                request.setAttribute("errorMessage", "Registration failed. Please try again.");

                // Preserve input values
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("email", email);
                request.setAttribute("contactNumber", contactNumber);

                // Regenerate CSRF token
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            // Show a user-friendly error message
            request.setAttribute("errorMessage", "An error occurred during registration. Please try again later.");

            // Preserve input values
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("contactNumber", contactNumber);

            // Regenerate CSRF token
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
