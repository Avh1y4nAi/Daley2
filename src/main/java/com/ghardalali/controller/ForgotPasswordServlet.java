package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ghardalali.model.UserDAO;
import com.ghardalali.util.PasswordUtil;

/**
 * Servlet for handling forgot password functionality
 */
@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to forgot password page
        request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        // Validate input
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email is required");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }

        // Check if email exists
        boolean emailExists = userDAO.isEmailExists(email);

        if (!emailExists) {
            // For security reasons, don't reveal that the email doesn't exist
            request.setAttribute("successMessage",
                    "If your email exists in our system, you will receive a password reset link shortly.");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }

        // In a real application, you would:
        // 1. Generate a unique token
        // 2. Store the token in the database with an expiration time
        // 3. Send an email with a link containing the token

        // For this demo, we'll just show a success message
        request.setAttribute("successMessage",
                "If your email exists in our system, you will receive a password reset link shortly.");
        request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    }
}
