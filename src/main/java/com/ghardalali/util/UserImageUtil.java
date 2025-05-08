package com.ghardalali.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling user profile image uploads
 */
public class UserImageUtil {
    
    // Base directory for user profile images
    private static final String USER_IMAGES_DIR = "images/users/";
    
    // Default profile image
    private static final String DEFAULT_PROFILE_IMAGE = "images/users/default-profile.jpg";
    
    /**
     * Uploads a user profile image and returns the relative path
     * 
     * @param request The HTTP request containing the file
     * @param partName The name of the file input field
     * @param userId The user ID (used for directory structure)
     * @return The relative path to the uploaded file, or null if upload failed
     * @throws IOException If an I/O error occurs
     * @throws ServletException If a servlet error occurs
     */
    public static String uploadProfileImage(HttpServletRequest request, String partName, int userId) 
            throws IOException, ServletException {
        Part filePart = request.getPart(partName);
        
        // Check if a file was actually uploaded
        if (filePart == null || filePart.getSize() <= 0 || filePart.getSubmittedFileName() == null 
                || filePart.getSubmittedFileName().trim().isEmpty()) {
            return null;
        }
        
        // Create directory structure
        String userDir = USER_IMAGES_DIR;
        String realPath = request.getServletContext().getRealPath("/");
        Path uploadDir = Paths.get(realPath, userDir);
        
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        
        // Generate a unique filename to prevent overwriting
        String originalFilename = filePart.getSubmittedFileName();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = "user_" + userId + "_" + UUID.randomUUID().toString() + fileExtension;
        
        // Save the file
        try (InputStream input = filePart.getInputStream()) {
            Path filePath = uploadDir.resolve(uniqueFilename);
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Return the relative path (for database storage)
            return userDir + uniqueFilename;
        }
    }
    
    /**
     * Deletes a user profile image from the server
     * 
     * @param request The HTTP request (used to get the real path)
     * @param relativePath The relative path of the file to delete
     * @return true if the file was deleted successfully, false otherwise
     */
    public static boolean deleteProfileImage(HttpServletRequest request, String relativePath) {
        if (relativePath == null || relativePath.trim().isEmpty() || relativePath.equals(DEFAULT_PROFILE_IMAGE)) {
            return false;
        }
        
        String realPath = request.getServletContext().getRealPath("/");
        Path filePath = Paths.get(realPath, relativePath);
        
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets the default profile image path
     * 
     * @return The default profile image path
     */
    public static String getDefaultProfileImage() {
        return DEFAULT_PROFILE_IMAGE;
    }
}
