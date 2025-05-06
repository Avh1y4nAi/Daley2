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
import com.ghardalali.util.ValidationUtil;

/**
 * Servlet for handling user profile page
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
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

        // Generate CSRF token for the form
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("activeTab", "profile");

        // Forward to profile page
        request.getRequestDispatcher("/new-profile.jsp").forward(request, response);
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String contactNumber = request.getParameter("contactNumber");
        String address = request.getParameter("address");

        // Validate input
        boolean hasError = false;
        StringBuilder errorMessage = new StringBuilder("Please correct the following errors:<br>");

        if (firstName == null || firstName.trim().isEmpty()) {
            errorMessage.append("- First name is required<br>");
            hasError = true;
        } else if (!ValidationUtil.isValidName(firstName)) {
            errorMessage.append("- First name contains invalid characters<br>");
            hasError = true;
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errorMessage.append("- Last name is required<br>");
            hasError = true;
        } else if (!ValidationUtil.isValidName(lastName)) {
            errorMessage.append("- Last name contains invalid characters<br>");
            hasError = true;
        }

        if (contactNumber != null && !contactNumber.trim().isEmpty() && !ValidationUtil.isValidPhone(contactNumber)) {
            errorMessage.append("- Phone number must be 10 digits<br>");
            hasError = true;
        }

        if (hasError) {
            // Set error message and form values
            request.setAttribute("errorMessage", errorMessage.toString());
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("contactNumber", contactNumber);
            request.setAttribute("address", address);

            // Set attributes in request
            request.setAttribute("user", user);
            request.setAttribute("activeTab", "profile");
            request.setAttribute("editMode", true);

            // Forward to profile page
            request.getRequestDispatcher("/new-profile.jsp").forward(request, response);
            return;
        }

        // Update user profile
        boolean success = userService.updateUserProfile(user.getUserId(), firstName, lastName, contactNumber, address);

        if (success) {
            // Update session user
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setContactNumber(contactNumber);
            user.setAddress(address);
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

        // Forward to profile page
        request.getRequestDispatcher("/new-profile.jsp").forward(request, response);
    }
}
