package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;

/**
 * Servlet for handling admin dashboard
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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

        // Set active tab to "dashboard" for proper navigation highlighting
        request.setAttribute("activeTab", "dashboard");

        // Forward to admin dashboard page
        request.getRequestDispatcher("/admin-dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
