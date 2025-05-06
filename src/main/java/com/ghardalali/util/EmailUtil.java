package com.ghardalali.util;

import java.util.Properties;

/**
 * Utility class for sending emails
 * 
 * NOTE: This is a placeholder implementation. In a production environment,
 * you would need to:
 * 1. Add Jakarta Mail API dependencies to your project
 * 2. Configure actual SMTP server settings
 * 3. Implement proper error handling and logging
 */
public class EmailUtil {
    
    // SMTP server configuration (to be configured in production)
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    
    // Sender email credentials (to be configured in production)
    private static final String SENDER_EMAIL = "noreply@ghardalali.com";
    private static final String SENDER_PASSWORD = "your_app_password_here";
    
    /**
     * Send an email (placeholder implementation)
     * 
     * @param toEmail Recipient email
     * @param subject Email subject
     * @param body Email body (HTML)
     * @return true if email sent successfully, false otherwise
     */
    public static boolean sendEmail(String toEmail, String subject, String body) {
        // Log the email details for debugging
        System.out.println("Email would be sent with the following details:");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body.substring(0, Math.min(100, body.length())) + "...");
        
        // In a real implementation, this would send the email using Jakarta Mail API
        // For now, we'll just return true to simulate successful sending
        return true;
    }
    
    /**
     * Send a password reset email
     * 
     * @param toEmail Recipient email
     * @param resetUrl Password reset URL
     * @param expiryHours Token expiry time in hours
     * @return true if email sent successfully, false otherwise
     */
    public static boolean sendPasswordResetEmail(String toEmail, String resetUrl, int expiryHours) {
        String subject = "Ghar Dalali - Password Reset";
        
        String body = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>"
                + "<h2 style='color: #4a6ee0;'>Ghar Dalali Password Reset</h2>"
                + "<p>Hello,</p>"
                + "<p>We received a request to reset your password. Click the button below to reset it:</p>"
                + "<p style='text-align: center;'>"
                + "<a href='" + resetUrl + "' style='display: inline-block; padding: 10px 20px; background-color: #4a6ee0; color: white; text-decoration: none; border-radius: 5px;'>Reset Password</a>"
                + "</p>"
                + "<p>If you didn't request a password reset, you can ignore this email.</p>"
                + "<p>This link will expire in " + expiryHours + " hours.</p>"
                + "<p>If the button above doesn't work, copy and paste the following URL into your browser:</p>"
                + "<p style='word-break: break-all;'><a href='" + resetUrl + "'>" + resetUrl + "</a></p>"
                + "<p>Regards,<br>The Ghar Dalali Team</p>"
                + "</div></body></html>";
        
        return sendEmail(toEmail, subject, body);
    }
    
    /**
     * Send a welcome email to a new user
     * 
     * @param toEmail Recipient email
     * @param fullName User's full name
     * @return true if email sent successfully, false otherwise
     */
    public static boolean sendWelcomeEmail(String toEmail, String fullName) {
        String subject = "Welcome to Ghar Dalali";
        
        String body = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 5px;'>"
                + "<h2 style='color: #4a6ee0;'>Welcome to Ghar Dalali!</h2>"
                + "<p>Hello " + fullName + ",</p>"
                + "<p>Thank you for registering with Ghar Dalali. We're excited to have you on board!</p>"
                + "<p>With your new account, you can:</p>"
                + "<ul>"
                + "<li>Browse properties</li>"
                + "<li>Save your favorite listings</li>"
                + "<li>Apply for properties</li>"
                + "<li>Contact property owners</li>"
                + "</ul>"
                + "<p style='text-align: center;'>"
                + "<a href='http://localhost:8080/ghar_dalali' style='display: inline-block; padding: 10px 20px; background-color: #4a6ee0; color: white; text-decoration: none; border-radius: 5px;'>Start Exploring</a>"
                + "</p>"
                + "<p>If you have any questions, feel free to contact our support team.</p>"
                + "<p>Regards,<br>The Ghar Dalali Team</p>"
                + "</div></body></html>";
        
        return sendEmail(toEmail, subject, body);
    }
}
