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
 * Servlet for handling user profile in dashboard
 */
@WebServlet("/dashboard/profile")
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

        // Get user from session
        User user = (User) session.getAttribute("user");

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("activeTab", "profile");

        // Forward to profile page
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
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

        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String contactNumber = request.getParameter("contactNumber");
        String address = request.getParameter("address");

        // Update user profile (placeholder for now)
        boolean success = true;

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
            request.setAttribute("errorMessage", "Failed to update profile");
        }

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("activeTab", "profile");

        // Forward to profile page
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
