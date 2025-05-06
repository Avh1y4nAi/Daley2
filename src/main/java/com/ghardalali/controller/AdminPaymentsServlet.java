package com.ghardalali.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;

/**
 * Servlet for handling admin payments management
 */
@WebServlet("/admin/payments")
public class AdminPaymentsServlet extends HttpServlet {
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

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("payments", new ArrayList<>()); // Empty list for now

        // Forward to admin payments page
        request.getRequestDispatcher("/admin-payments.jsp").forward(request, response);
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

        // Get action parameter
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            // Delete payment
            int paymentId = Integer.parseInt(request.getParameter("paymentId"));
            // Placeholder for payment deletion
            boolean success = true;

            if (success) {
                request.setAttribute("successMessage", "Payment deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete payment");
            }
        }

        // Redirect back to admin payments page
        response.sendRedirect(request.getContextPath() + "/admin/payments");
    }
}
