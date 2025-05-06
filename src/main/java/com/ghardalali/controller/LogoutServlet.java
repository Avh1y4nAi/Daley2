package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.util.SessionUtil;

/**
 * Servlet for handling user logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Log the logout attempt
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            System.out.println("User logged out: " + session.getAttribute("user"));
        }

        // Perform logout
        SessionUtil.logoutUser(request, response);

        // Set success message in a new session
        session = request.getSession(true);
        session.setAttribute("successMessage", "You have been successfully logged out.");

        // Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
