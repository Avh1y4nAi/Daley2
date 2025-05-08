package com.ghardalali.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.ghardalali.util.MessageUtil;
import com.ghardalali.util.NavigationUtil;

/**
 * Base servlet class with common functionality for all servlets
 */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Initialize the servlet
     */
    @Override
    public void init() throws ServletException {
        super.init();
    }
    
    /**
     * Process request before handling it
     * This method is called by all servlets before processing the request
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException if a servlet-specific error occurs
     */
    protected void preProcessRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException {
        // Process flash messages
        MessageUtil.processFlashMessages(request);
        
        // Set active tab based on request URI
        NavigationUtil.setActiveTabFromURI(request);
    }
    
    /**
     * Set the active tab for navigation
     * 
     * @param request HTTP request
     * @param tabName Name of the active tab
     */
    protected void setActiveTab(HttpServletRequest request, String tabName) {
        NavigationUtil.setActiveTab(request, tabName);
    }
    
    /**
     * Set a success message in the request
     * 
     * @param request HTTP request
     * @param message Success message
     */
    protected void setSuccessMessage(HttpServletRequest request, String message) {
        MessageUtil.setSuccessMessage(request, message);
    }
    
    /**
     * Set an error message in the request
     * 
     * @param request HTTP request
     * @param message Error message
     */
    protected void setErrorMessage(HttpServletRequest request, String message) {
        MessageUtil.setErrorMessage(request, message);
    }
    
    /**
     * Set an info message in the request
     * 
     * @param request HTTP request
     * @param message Info message
     */
    protected void setInfoMessage(HttpServletRequest request, String message) {
        MessageUtil.setInfoMessage(request, message);
    }
    
    /**
     * Set a warning message in the request
     * 
     * @param request HTTP request
     * @param message Warning message
     */
    protected void setWarningMessage(HttpServletRequest request, String message) {
        MessageUtil.setWarningMessage(request, message);
    }
    
    /**
     * Set a flash success message (persists across redirects)
     * 
     * @param request HTTP request
     * @param message Success message
     */
    protected void setFlashSuccessMessage(HttpServletRequest request, String message) {
        MessageUtil.setFlashSuccessMessage(request, message);
    }
    
    /**
     * Set a flash error message (persists across redirects)
     * 
     * @param request HTTP request
     * @param message Error message
     */
    protected void setFlashErrorMessage(HttpServletRequest request, String message) {
        MessageUtil.setFlashErrorMessage(request, message);
    }
    
    /**
     * Set a flash info message (persists across redirects)
     * 
     * @param request HTTP request
     * @param message Info message
     */
    protected void setFlashInfoMessage(HttpServletRequest request, String message) {
        MessageUtil.setFlashInfoMessage(request, message);
    }
    
    /**
     * Set a flash warning message (persists across redirects)
     * 
     * @param request HTTP request
     * @param message Warning message
     */
    protected void setFlashWarningMessage(HttpServletRequest request, String message) {
        MessageUtil.setFlashWarningMessage(request, message);
    }
}
