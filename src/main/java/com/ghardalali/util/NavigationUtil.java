package com.ghardalali.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for handling navigation-related functionality
 * This replaces client-side navigation highlighting with server-side logic
 */
public class NavigationUtil {
    
    private static final String ACTIVE_TAB_ATTRIBUTE = "activeTab";
    
    /**
     * Sets the active tab in the request
     * @param request The HTTP request
     * @param tabName The name of the active tab
     */
    public static void setActiveTab(HttpServletRequest request, String tabName) {
        request.setAttribute(ACTIVE_TAB_ATTRIBUTE, tabName);
    }
    
    /**
     * Determines the active tab based on the request URI
     * @param request The HTTP request
     * @return The name of the active tab
     */
    public static String determineActiveTab(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        
        // Remove context path from URI if present
        if (uri.startsWith(contextPath)) {
            uri = uri.substring(contextPath.length());
        }
        
        // Determine active tab based on URI
        if (uri.equals("/") || uri.equals("/index.jsp") || uri.isEmpty()) {
            return "home";
        } else if (uri.contains("/properties")) {
            return "properties";
        } else if (uri.contains("/about")) {
            return "about";
        } else if (uri.contains("/contact")) {
            return "contact";
        } else if (uri.contains("/profile")) {
            return "profile";
        } else if (uri.contains("/dashboard")) {
            return "dashboard";
        } else if (uri.contains("/admin/dashboard")) {
            return "admin-dashboard";
        } else if (uri.contains("/admin/properties")) {
            return "admin-properties";
        } else if (uri.contains("/admin/users")) {
            return "admin-users";
        } else if (uri.contains("/admin/applications")) {
            return "admin-applications";
        } else if (uri.contains("/admin/reviews")) {
            return "admin-reviews";
        }
        
        // Default to home if no match
        return "home";
    }
    
    /**
     * Sets the active tab based on the request URI
     * @param request The HTTP request
     */
    public static void setActiveTabFromURI(HttpServletRequest request) {
        String activeTab = determineActiveTab(request);
        setActiveTab(request, activeTab);
    }
    
    /**
     * Checks if a tab is active
     * @param request The HTTP request
     * @param tabName The name of the tab to check
     * @return true if the tab is active, false otherwise
     */
    public static boolean isActiveTab(HttpServletRequest request, String tabName) {
        Object activeTab = request.getAttribute(ACTIVE_TAB_ATTRIBUTE);
        return activeTab != null && activeTab.toString().equals(tabName);
    }
    
    /**
     * Gets the CSS class for a tab (active or empty)
     * @param request The HTTP request
     * @param tabName The name of the tab
     * @return "active" if the tab is active, empty string otherwise
     */
    public static String getTabClass(HttpServletRequest request, String tabName) {
        return isActiveTab(request, tabName) ? "active" : "";
    }
}
