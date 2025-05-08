package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.ghardalali.model.User;
import com.ghardalali.service.UserService;
import com.ghardalali.util.SessionUtil;
import com.ghardalali.util.UserImageUtil;

/**
 * Servlet for handling admin profile functionality
 */
@WebServlet("/admin/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminProfileServlet extends BaseServlet {
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

        // Set active tab to "profile" for proper navigation highlighting
        request.setAttribute("activeTab", "profile");

        // Generate CSRF token if needed
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Forward to admin profile page
        request.getRequestDispatcher("/admin-profile.jsp").forward(request, response);
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
            request.setAttribute("activeTab", "profile");
            request.getRequestDispatcher("/admin-profile.jsp").forward(request, response);
            return;
        }

        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contactNumber");
        String address = request.getParameter("address");

        // Check if this is a profile image upload request
        String action = request.getParameter("action");
        if ("uploadProfileImage".equals(action)) {
            try {
                // Get the uploaded file
                Part filePart = request.getPart("profileImage");

                if (filePart != null && filePart.getSize() > 0) {
                    // Upload the profile image
                    String profileImagePath = UserImageUtil.uploadProfileImage(request, "profileImage",
                            user.getUserId());

                    if (profileImagePath != null) {
                        // Update the user's profile image in the database
                        boolean imageUpdateSuccess = userService.updateProfileImage(user.getUserId(), profileImagePath);

                        if (imageUpdateSuccess) {
                            // Update the user in the session
                            user.setProfileImagePath(profileImagePath);
                            session.setAttribute("user", user);

                            // Set success message
                            request.setAttribute("successMessage", "Profile image updated successfully");
                        } else {
                            // Set error message
                            request.setAttribute("errorMessage", "Failed to update profile image. Please try again.");
                        }
                    } else {
                        // Set error message
                        request.setAttribute("errorMessage", "Failed to upload profile image. Please try again.");
                    }
                } else {
                    // Set error message
                    request.setAttribute("errorMessage", "No image selected. Please select an image to upload.");
                }

                // Set attributes in request
                request.setAttribute("user", user);
                request.setAttribute("activeTab", "profile");

                // Forward to admin profile page
                request.getRequestDispatcher("/admin-profile.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error uploading profile image: " + e.getMessage());
                request.setAttribute("user", user);
                request.setAttribute("activeTab", "profile");
                request.getRequestDispatcher("/admin-profile.jsp").forward(request, response);
                return;
            }
        }

        // Validate input
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {

            request.setAttribute("errorMessage", "First name, last name, and email are required");
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "profile");
            request.getRequestDispatcher("/admin-profile.jsp").forward(request, response);
            return;
        }

        // Update user information
        user.setFirstName(firstName);
        user.setLastName(lastName);
        // Note: Email update is not supported by the updateUserProfile method
        user.setContactNumber(contactNumber);
        user.setAddress(address);

        // Save updated user
        boolean success = userService.updateUserProfile(user.getUserId(), firstName, lastName, contactNumber, address);

        if (success) {
            // Update user in session
            session.setAttribute("user", user);

            // Set success message
            request.setAttribute("successMessage", "Profile updated successfully");
        } else {
            // Set error message
            request.setAttribute("errorMessage", "Failed to update profile. Please try again later.");
        }

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("activeTab", "profile");

        // Forward to admin profile page
        request.getRequestDispatcher("/admin-profile.jsp").forward(request, response);
    }
}
