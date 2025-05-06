package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.service.UserService;

/**
 * Servlet for handling user registration
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // User is already logged in, redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Forward to registration page
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contactNumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String termsAgreed = request.getParameter("termsAgreed");

        // Validate input
        boolean hasError = false;

        // First name validation
        if (firstName == null || firstName.trim().isEmpty()) {
            request.setAttribute("firstNameError", "First name is required");
            hasError = true;
        } else if (firstName.length() > 50) {
            request.setAttribute("firstNameError", "First name cannot exceed 50 characters");
            hasError = true;
        }

        // Last name validation
        if (lastName == null || lastName.trim().isEmpty()) {
            request.setAttribute("lastNameError", "Last name is required");
            hasError = true;
        } else if (lastName.length() > 50) {
            request.setAttribute("lastNameError", "Last name cannot exceed 50 characters");
            hasError = true;
        }

        // Email validation
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required");
            hasError = true;
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("emailError", "Please enter a valid email address");
            hasError = true;
        } else if (userService.isEmailExists(email)) {
            request.setAttribute("emailError",
                    "This email is already registered. Please use a different email or login.");
            hasError = true;
        }

        // Contact number validation
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            request.setAttribute("contactNumberError", "Contact number is required");
            hasError = true;
        } else if (!contactNumber.matches("^[0-9]{10}$")) {
            request.setAttribute("contactNumberError", "Please enter a valid 10-digit contact number");
            hasError = true;
        }

        // Password validation
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required");
            hasError = true;
        } else if (password.length() < 8) {
            request.setAttribute("passwordError", "Password must be at least 8 characters long");
            hasError = true;
        } else if (!password.matches(".*[A-Z].*")) {
            request.setAttribute("passwordError", "Password must contain at least one uppercase letter");
            hasError = true;
        } else if (!password.matches(".*[0-9].*")) {
            request.setAttribute("passwordError", "Password must contain at least one number");
            hasError = true;
        }

        // Confirm password validation
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
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
            // Preserve input values
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("contactNumber", contactNumber);

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

            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
