package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.service.UserService;

/**
 * Servlet for handling user login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // User is already logged in, redirect based on role
            User user = (User) session.getAttribute("user");
            if (user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
            return;
        }

        // Check for "remember me" cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    request.setAttribute("rememberedEmail", cookie.getValue());
                    break;
                }
            }
        }

        // Forward to login page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Validate input
        boolean hasError = false;

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required");
            hasError = true;
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("emailError", "Please enter a valid email address");
            hasError = true;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("passwordError", "Password is required");
            hasError = true;
        }

        if (hasError) {
            // Preserve email for convenience
            request.setAttribute("email", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            // Authenticate user
            User user = userService.loginUser(email, password);

            if (user != null) {
                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Set session timeout to 30 minutes
                session.setMaxInactiveInterval(30 * 60);

                // Set "remember me" cookie if requested
                if (rememberMe != null && rememberMe.equals("on")) {
                    Cookie emailCookie = new Cookie("email", email);
                    emailCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                    emailCookie.setHttpOnly(true); // For security
                    emailCookie.setPath("/"); // Available throughout the application
                    response.addCookie(emailCookie);
                } else {
                    // Delete any existing "remember me" cookie
                    Cookie emailCookie = new Cookie("email", "");
                    emailCookie.setMaxAge(0);
                    emailCookie.setPath("/");
                    response.addCookie(emailCookie);
                }

                // Log successful login
                System.out.println("User logged in: " + email);

                // Redirect based on user role
                if (user.isAdmin()) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                }
            } else {
                // Authentication failed
                request.setAttribute("errorMessage", "Invalid email or password");
                request.setAttribute("email", email); // Preserve email for convenience
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();

            // Show a user-friendly error message
            request.setAttribute("errorMessage", "An error occurred during login. Please try again later.");
            request.setAttribute("email", email); // Preserve email for convenience
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
