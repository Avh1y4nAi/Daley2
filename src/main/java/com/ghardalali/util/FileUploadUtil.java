package com.ghardalali.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling file uploads
 */
public class FileUploadUtil {
    
    // Base directory for property images
    private static final String PROPERTY_IMAGES_DIR = "images/properties/";
    
    /**
     * Uploads a property image and returns the relative path
     * 
     * @param request The HTTP request containing the file
     * @param partName The name of the file input field
     * @param propertyId The property ID (used for directory structure)
     * @return The relative path to the uploaded file, or null if upload failed
     * @throws IOException If an I/O error occurs
     * @throws ServletException If a servlet error occurs
     */
    public static String uploadPropertyImage(HttpServletRequest request, String partName, int propertyId) 
            throws IOException, ServletException {
        Part filePart = request.getPart(partName);
        
        // Check if a file was actually uploaded
        if (filePart == null || filePart.getSize() <= 0 || filePart.getSubmittedFileName() == null 
                || filePart.getSubmittedFileName().trim().isEmpty()) {
            return null;
        }
        
        // Create directory structure
        String propertyDir = PROPERTY_IMAGES_DIR + propertyId + "/";
        String realPath = request.getServletContext().getRealPath("/");
        Path uploadDir = Paths.get(realPath, propertyDir);
        
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        
        // Generate a unique filename to prevent overwriting
        String originalFilename = filePart.getSubmittedFileName();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        
        // Save the file
        try (InputStream input = filePart.getInputStream()) {
            Path filePath = uploadDir.resolve(uniqueFilename);
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Return the relative path (for database storage)
            return propertyDir + uniqueFilename;
        }
    }
    
    /**
     * Uploads multiple property images and returns the relative paths
     * 
     * @param request The HTTP request containing the files
     * @param partNames The names of the file input fields
     * @param propertyId The property ID (used for directory structure)
     * @return List of relative paths to the uploaded files
     * @throws IOException If an I/O error occurs
     * @throws ServletException If a servlet error occurs
     */
    public static List<String> uploadPropertyImages(HttpServletRequest request, List<String> partNames, int propertyId) 
            throws IOException, ServletException {
        List<String> imagePaths = new ArrayList<>();
        
        for (String partName : partNames) {
            String path = uploadPropertyImage(request, partName, propertyId);
            if (path != null) {
                imagePaths.add(path);
            }
        }
        
        return imagePaths;
    }
    
    /**
     * Deletes a file from the server
     * 
     * @param request The HTTP request (used to get the real path)
     * @param relativePath The relative path of the file to delete
     * @return true if the file was deleted successfully, false otherwise
     */
    public static boolean deleteFile(HttpServletRequest request, String relativePath) {
        if (relativePath == null || relativePath.trim().isEmpty()) {
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
}
