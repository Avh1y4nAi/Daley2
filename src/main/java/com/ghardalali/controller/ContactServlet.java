package com.ghardalali.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ghardalali.util.DBUtil;

/**
 * Servlet for handling contact page and form submissions
 */
@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to contact page
        request.getRequestDispatcher("/contact.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        
        // Validate input
        if (name == null || name.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            subject == null || subject.trim().isEmpty() || 
            message == null || message.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "Please fill in all required fields");
            request.getRequestDispatcher("/contact.jsp").forward(request, response);
            return;
        }
        
        // Save message to database
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO contact_messages (name, email, phone, subject, message) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, subject);
            pstmt.setString(5, message);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Message saved successfully
                request.setAttribute("successMessage", "Your message has been sent successfully. We will get back to you soon!");
            } else {
                // Failed to save message
                request.setAttribute("errorMessage", "Failed to send message. Please try again later.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your request. Please try again later.");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) DBUtil.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Forward back to contact page with success/error message
        request.getRequestDispatcher("/contact.jsp").forward(request, response);
    }
}
