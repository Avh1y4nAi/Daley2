package com.ghardalali.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ghardalali.util.SessionUtil;

/**
 * Filter to add security headers and CSRF protection
 */
@WebFilter(urlPatterns = { "/*" })
public class SecurityFilter implements Filter {

    // List of HTTP methods that should be protected by CSRF
    private static final Set<String> CSRF_PROTECTED_METHODS = new HashSet<>(
            Arrays.asList("POST", "PUT", "DELETE", "PATCH"));
    
    // List of paths that should be excluded from CSRF protection
    private static final Set<String> CSRF_EXCLUDED_PATHS = new HashSet<>(
            Arrays.asList("/api/", "/public/"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Add security headers
        addSecurityHeaders(httpResponse);
        
        // Check CSRF token for state-changing requests
        if (requiresCSRFProtection(httpRequest) && !isValidCSRFToken(httpRequest)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
            return;
        }
        
        // Generate CSRF token if not present
        generateCSRFTokenIfNeeded(httpRequest);
        
        // Continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
    
    /**
     * Add security headers to the response
     * 
     * @param response HTTP response
     */
    private void addSecurityHeaders(HttpServletResponse response) {
        // Prevent clickjacking
        response.setHeader("X-Frame-Options", "DENY");
        
        // Enable XSS protection
        response.setHeader("X-XSS-Protection", "1; mode=block");
        
        // Prevent MIME type sniffing
        response.setHeader("X-Content-Type-Options", "nosniff");
        
        // Strict Transport Security (HSTS)
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        
        // Content Security Policy
        response.setHeader("Content-Security-Policy", 
                "default-src 'self'; script-src 'self' 'unsafe-inline' https://fonts.googleapis.com; " +
                "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; " +
                "font-src 'self' https://fonts.gstatic.com; " +
                "img-src 'self' data:; " +
                "connect-src 'self'");
        
        // Referrer Policy
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        
        // Permissions Policy
        response.setHeader("Permissions-Policy", 
                "camera=(), microphone=(), geolocation=(), payment=()");
    }
    
    /**
     * Check if the request requires CSRF protection
     * 
     * @param request HTTP request
     * @return true if CSRF protection is required, false otherwise
     */
    private boolean requiresCSRFProtection(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        
        // Check if method requires CSRF protection
        if (!CSRF_PROTECTED_METHODS.contains(method)) {
            return false;
        }
        
        // Check if path is excluded from CSRF protection
        for (String excludedPath : CSRF_EXCLUDED_PATHS) {
            if (path.startsWith(request.getContextPath() + excludedPath)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Check if the CSRF token is valid
     * 
     * @param request HTTP request
     * @return true if valid, false otherwise
     */
    private boolean isValidCSRFToken(HttpServletRequest request) {
        // Get token from request
        String requestToken = request.getParameter("csrfToken");
        
        // If no token in request, check header
        if (requestToken == null || requestToken.isEmpty()) {
            requestToken = request.getHeader("X-CSRF-TOKEN");
        }
        
        // Validate token
        return SessionUtil.isValidCSRFToken(request, requestToken);
    }
    
    /**
     * Generate a CSRF token if not already present in session
     * 
     * @param request HTTP request
     */
    private void generateCSRFTokenIfNeeded(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if (session != null && session.getAttribute(SessionUtil.CSRF_TOKEN_ATTR) == null) {
            session.setAttribute(SessionUtil.CSRF_TOKEN_ATTR, SessionUtil.generateCSRFToken());
        }
    }
}
