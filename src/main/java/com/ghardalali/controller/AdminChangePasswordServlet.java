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
import com.ghardalali.util.SessionUtil;

/**
 * Servlet for handling admin password change functionality
 */
@WebServlet("/admin/change-password")
public class AdminChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Set user in request attribute
        request.setAttribute("user", user);

        // Set active tab to "change-password" for proper navigation highlighting
        request.setAttribute("activeTab", "change-password");

        // Generate CSRF token if not exists
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Forward to admin change password page
        request.getRequestDispatcher("/admin-change-password.jsp").forward(request, response);
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

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Check if user is admin
        if (!user.isAdmin()) {
            // User is not admin, redirect to regular dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            request.setAttribute("errorMessage", "Invalid request. Please try again.");
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "change-password");
            request.getRequestDispatcher("/admin-change-password.jsp").forward(request, response);
            return;
        }

        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required");
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "change-password");
            request.getRequestDispatcher("/admin-change-password.jsp").forward(request, response);
            return;
        }

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New password and confirm password do not match");
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "change-password");
            request.getRequestDispatcher("/admin-change-password.jsp").forward(request, response);
            return;
        }

        // Validate password strength
        if (newPassword.length() < 8) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters long");
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "change-password");
            request.getRequestDispatcher("/admin-change-password.jsp").forward(request, response);
            return;
        }

        // Update password
        boolean success = userService.updatePassword(user.getUserId(), currentPassword, newPassword);

        if (success) {
            // Password updated successfully
            request.setAttribute("successMessage", "Password updated successfully");
        } else {
            // Failed to update password
            request.setAttribute("errorMessage",
                    "Failed to update password. Please check your current password and try again.");
        }

        request.setAttribute("user", user);
        request.setAttribute("activeTab", "change-password");
        request.getRequestDispatcher("/admin-change-password.jsp").forward(request, response);
    }
}
