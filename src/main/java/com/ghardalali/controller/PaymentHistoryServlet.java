package com.ghardalali.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;

/**
 * Servlet for handling payment history in dashboard
 */
@WebServlet("/dashboard/payment-history")
public class PaymentHistoryServlet extends BaseServlet {
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

        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("payments", new ArrayList<>()); // Empty list for now
        request.setAttribute("activeTab", "payment-history");

        // Forward to payment history page
        request.getRequestDispatcher("/payment-history.jsp").forward(request, response);
    }
}
