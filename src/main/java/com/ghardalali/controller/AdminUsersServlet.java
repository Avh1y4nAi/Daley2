package com.ghardalali.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.model.User;
import com.ghardalali.service.UserService;

/**
 * Servlet for handling admin users management
 */
@WebServlet("/admin/users")
public class AdminUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
        // Get all users
        List<User> users = userService.getAllUsers();
        
        // Set attributes in request
        request.setAttribute("user", user);
        request.setAttribute("users", users);
        
        // Forward to admin users page
        request.getRequestDispatcher("/admin-users.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            // Delete user
            int userId = Integer.parseInt(request.getParameter("userId"));
            boolean success = userService.deleteUser(userId);
            
            if (success) {
                request.setAttribute("successMessage", "User deleted successfully");
            } else {
                request.setAttribute("errorMessage", "Failed to delete user");
            }
        }
        
        // Redirect back to admin users page
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
