package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.service.UserService;
import com.ghardalali.util.SessionUtil;
import com.ghardalali.util.ValidationUtil;

/**
 * Servlet for handling forgot password functionality
 */
@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Generate CSRF token for the form
        HttpSession session = request.getSession(true);
        if (session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }

        // Forward to forgot password page
        request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify CSRF token
        String csrfToken = request.getParameter("csrfToken");
        if (!SessionUtil.isValidCSRFToken(request, csrfToken)) {
            request.setAttribute("errorMessage", "Invalid request. Please try again.");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }

        String email = request.getParameter("email");

        // Sanitize input
        email = ValidationUtil.sanitizeInput(email);

        // Validate input
        if (ValidationUtil.isNullOrEmpty(email)) {
            request.setAttribute("emailError", "Email is required");

            // Regenerate CSRF token
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        } else if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Please enter a valid email address");

            // Regenerate CSRF token
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
            return;
        }

        // Get base URL for reset link
        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }
        baseUrl += request.getContextPath();

        // Create password reset token and send email
        boolean success = userService.createPasswordResetTokenAndSendEmail(email, baseUrl);

        // For security reasons, always show the same message whether the email exists
        // or not
        request.setAttribute("successMessage",
                "If your email exists in our system, you will receive a password reset link shortly.");

        // Log the result (for debugging)
        if (success) {
            System.out.println("Password reset token created and email sent for: " + email);
        } else {
            System.out.println("Failed to create password reset token or send email for: " + email);
        }

        // Regenerate CSRF token
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());

        request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    }
}
