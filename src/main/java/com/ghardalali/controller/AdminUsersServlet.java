package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

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
 * Servlet for handling admin users management
 */
@WebServlet("/admin/users")
public class AdminUsersServlet extends BaseServlet {
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

        // Get all users
        List<User> users = userService.getAllUsers();

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("users", users);

        // Set active tab to "users" for proper navigation highlighting
        request.setAttribute("activeTab", "users");

        // Forward to admin users page
        request.getRequestDispatcher("/admin-users.jsp").forward(request, response);
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
            session.setAttribute("errorMessage", "Invalid CSRF token. Please try again.");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        // Get action parameter
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Delete user
            try {
                int userId = Integer.parseInt(request.getParameter("userId"));

                // Don't allow deleting yourself
                if (userId == user.getUserId()) {
                    session.setAttribute("errorMessage", "You cannot delete your own account");
                } else {
                    boolean success = userService.deleteUser(userId);

                    if (success) {
                        session.setAttribute("successMessage", "User deleted successfully");
                    } else {
                        session.setAttribute("errorMessage", "Failed to delete user");
                    }
                }
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid user ID");
            }
        } else if ("edit".equals(action)) {
            // Edit user
            try {
                int userId = Integer.parseInt(request.getParameter("userId"));
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String userRole = request.getParameter("userRole");

                // Validate input
                if (fullName == null || fullName.trim().isEmpty()) {
                    session.setAttribute("errorMessage", "Name cannot be empty");
                } else if (email == null || email.trim().isEmpty() || !ValidationUtil.isValidEmail(email)) {
                    session.setAttribute("errorMessage", "Invalid email address");
                } else if (userRole == null || (!userRole.equals("ADMIN") && !userRole.equals("USER"))) {
                    session.setAttribute("errorMessage", "Invalid user role");
                } else {
                    // Get existing user
                    User existingUser = userService.getUserById(userId);

                    if (existingUser == null) {
                        session.setAttribute("errorMessage", "User not found");
                    } else {
                        // Check if email is already in use by another user
                        if (!email.equals(existingUser.getEmail()) && userService.isEmailExists(email)) {
                            session.setAttribute("errorMessage", "Email is already in use by another user");
                        } else {
                            // Split full name into first and last name
                            String firstName = fullName.split(" ")[0];
                            String lastName = fullName.contains(" ") ? fullName.substring(fullName.indexOf(" ") + 1)
                                    : "";

                            // Update user
                            existingUser.setFirstName(firstName);
                            existingUser.setLastName(lastName);

                            // Note: Email update is not directly supported by updateUserProfile
                            // We'll need to handle this separately if needed

                            // For now, we'll just update the profile fields
                            boolean success = userService.updateUserProfile(
                                    existingUser.getUserId(),
                                    firstName,
                                    lastName,
                                    existingUser.getContactNumber(),
                                    existingUser.getAddress());

                            if (success) {
                                session.setAttribute("successMessage", "User updated successfully");

                                // If the user is updating their own account, update the session
                                if (userId == user.getUserId()) {
                                    session.setAttribute("user", existingUser);
                                }
                            } else {
                                session.setAttribute("errorMessage", "Failed to update user");
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid user ID");
            }
        }

        // Redirect back to admin users page
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
