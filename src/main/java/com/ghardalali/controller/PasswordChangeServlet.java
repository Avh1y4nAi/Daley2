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
 * Servlet for handling password change
 */
@WebServlet("/profile/change-password")
public class PasswordChangeServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to profile page
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            request.setAttribute("errorMessage", "Invalid request. Please try again.");
            request.getRequestDispatcher("/new-profile.jsp").forward(request, response);
            return;
        }

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        boolean hasError = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:<br>");

        if (currentPassword == null || currentPassword.trim().isEmpty()) {
            errorMessage.append("- Current password is required<br>");
            hasError = true;
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            errorMessage.append("- New password is required<br>");
            hasError = true;
        } else if (!ValidationUtil.isStrongPassword(newPassword)) {
            errorMessage.append("- New password does not meet security requirements<br>");
            hasError = true;
        }

        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            errorMessage.append("- Confirm password is required<br>");
            hasError = true;
        } else if (!confirmPassword.equals(newPassword)) {
            errorMessage.append("- Passwords do not match<br>");
            hasError = true;
        }

        // Verify current password
        if (!hasError && !com.ghardalali.util.PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
            errorMessage.append("- Current password is incorrect<br>");
            hasError = true;
        }

        if (hasError) {
            // Set error message
            request.setAttribute("errorMessage", errorMessage.toString());

            // Set attributes in request
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "profile");

            // Forward to profile page with password section active
            request.setAttribute("activeSection", "password");
            request.getRequestDispatcher("/new-profile.jsp").forward(request, response);
            return;
        }

        // Update password
        boolean success = userService.updatePassword(user.getUserId(), currentPassword, newPassword);

        if (success) {
            // Set success message
            request.setAttribute("successMessage", "Password updated successfully");
        } else {
            // Set error message
            request.setAttribute("errorMessage", "Failed to update password. Please try again later.");
        }

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("activeTab", "profile");

        // Forward to profile page
        request.getRequestDispatcher("/new-profile.jsp").forward(request, response);
    }
}
