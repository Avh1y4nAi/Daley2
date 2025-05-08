package com.ghardalali.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for handling messages in the application
 * This replaces client-side modal dialogs with server-side messages
 */
public class MessageUtil {
    
    private static final String SUCCESS_MESSAGE = "successMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String INFO_MESSAGE = "infoMessage";
    private static final String WARNING_MESSAGE = "warningMessage";
    
    /**
     * Sets a success message in the request
     * @param request The HTTP request
     * @param message The success message
     */
    public static void setSuccessMessage(HttpServletRequest request, String message) {
        request.setAttribute(SUCCESS_MESSAGE, message);
    }
    
    /**
     * Sets an error message in the request
     * @param request The HTTP request
     * @param message The error message
     */
    public static void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute(ERROR_MESSAGE, message);
    }
    
    /**
     * Sets an info message in the request
     * @param request The HTTP request
     * @param message The info message
     */
    public static void setInfoMessage(HttpServletRequest request, String message) {
        request.setAttribute(INFO_MESSAGE, message);
    }
    
    /**
     * Sets a warning message in the request
     * @param request The HTTP request
     * @param message The warning message
     */
    public static void setWarningMessage(HttpServletRequest request, String message) {
        request.setAttribute(WARNING_MESSAGE, message);
    }
    
    /**
     * Sets a flash message in the session (persists across redirects)
     * @param request The HTTP request
     * @param type The message type (success, error, info, warning)
     * @param message The message
     */
    public static void setFlashMessage(HttpServletRequest request, String type, String message) {
        HttpSession session = request.getSession(true);
        session.setAttribute("flash_" + type, message);
    }
    
    /**
     * Gets and removes flash messages from the session
     * @param request The HTTP request
     */
    public static void processFlashMessages(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Process success message
            String successMessage = (String) session.getAttribute("flash_success");
            if (successMessage != null) {
                request.setAttribute(SUCCESS_MESSAGE, successMessage);
                session.removeAttribute("flash_success");
            }
            
            // Process error message
            String errorMessage = (String) session.getAttribute("flash_error");
            if (errorMessage != null) {
                request.setAttribute(ERROR_MESSAGE, errorMessage);
                session.removeAttribute("flash_error");
            }
            
            // Process info message
            String infoMessage = (String) session.getAttribute("flash_info");
            if (infoMessage != null) {
                request.setAttribute(INFO_MESSAGE, infoMessage);
                session.removeAttribute("flash_info");
            }
            
            // Process warning message
            String warningMessage = (String) session.getAttribute("flash_warning");
            if (warningMessage != null) {
                request.setAttribute(WARNING_MESSAGE, warningMessage);
                session.removeAttribute("flash_warning");
            }
        }
    }
    
    /**
     * Sets a flash success message (persists across redirects)
     * @param request The HTTP request
     * @param message The success message
     */
    public static void setFlashSuccessMessage(HttpServletRequest request, String message) {
        setFlashMessage(request, "success", message);
    }
    
    /**
     * Sets a flash error message (persists across redirects)
     * @param request The HTTP request
     * @param message The error message
     */
    public static void setFlashErrorMessage(HttpServletRequest request, String message) {
        setFlashMessage(request, "error", message);
    }
    
    /**
     * Sets a flash info message (persists across redirects)
     * @param request The HTTP request
     * @param message The info message
     */
    public static void setFlashInfoMessage(HttpServletRequest request, String message) {
        setFlashMessage(request, "info", message);
    }
    
    /**
     * Sets a flash warning message (persists across redirects)
     * @param request The HTTP request
     * @param message The warning message
     */
    public static void setFlashWarningMessage(HttpServletRequest request, String message) {
        setFlashMessage(request, "warning", message);
    }
}
