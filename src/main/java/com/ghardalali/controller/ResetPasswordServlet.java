package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.service.UserService;
import com.ghardalali.util.SessionUtil;
import com.ghardalali.util.ValidationUtil;

/**
 * Servlet for handling password reset confirmation
 */
@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get token from request
        String token = request.getParameter("token");
        
        if (token == null || token.trim().isEmpty()) {
            // No token provided, redirect to forgot password page
            request.setAttribute("errorMessage", "Invalid or missing reset token. Please try again.");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }
        
        // Validate token
        String email = userService.validatePasswordResetToken(token);
        
        if (email == null) {
            // Invalid or expired token
            request.setAttribute("errorMessage", "Invalid or expired reset token. Please request a new one.");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }
        
        // Token is valid, store it in session for the form submission
        HttpSession session = request.getSession(true);
        session.setAttribute("resetToken", token);
        
        // Generate CSRF token for the form
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }
        
        // Forward to reset password page
        request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            request.setAttribute("errorMessage", "Invalid request. Please try again.");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }
        
        // Get token from session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("resetToken") == null) {
            // No token in session
            request.setAttribute("errorMessage", "Invalid session. Please try again.");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }
        
        String token = (String) session.getAttribute("resetToken");
        
        // Get form parameters
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate input
        boolean hasError = false;
        
        if (ValidationUtil.isNullOrEmpty(newPassword)) {
            request.setAttribute("newPasswordError", "New password is required");
            hasError = true;
        } else if (!ValidationUtil.isBasicPassword(newPassword)) {
            request.setAttribute("newPasswordError", "Password must be at least 8 characters long");
            hasError = true;
        } else {
            // Check password strength
            int passwordStrength = ValidationUtil.getPasswordStrength(newPassword);
            if (passwordStrength < 3) {
                request.setAttribute("newPasswordError", 
                    "Password is too weak. Include uppercase letters, numbers, and special characters.");
                hasError = true;
            }
        }
        
        if (ValidationUtil.isNullOrEmpty(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Please confirm your password");
            hasError = true;
        } else if (!confirmPassword.equals(newPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match");
            hasError = true;
        }
        
        if (hasError) {
            // Regenerate CSRF token
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
            
            // Forward back to reset password page with errors
            request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
            return;
        }
        
        // Reset password
        boolean success = userService.resetPassword(token, newPassword);
        
        if (success) {
            // Remove token from session
            session.removeAttribute("resetToken");
            
            // Password reset successful
            request.setAttribute("successMessage", "Your password has been reset successfully. You can now login with your new password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            // Password reset failed
            request.setAttribute("errorMessage", "Failed to reset password. Please try again.");
            
            // Regenerate CSRF token
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
            
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
        }
    }
}
