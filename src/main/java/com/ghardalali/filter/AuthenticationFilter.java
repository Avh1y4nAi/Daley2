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

import com.ghardalali.model.User;
import com.ghardalali.util.SessionUtil;

/**
 * Filter to check if user is authenticated for protected pages
 */
@WebFilter(urlPatterns = {
        "/dashboard/*",
        "/checkout",
        "/profile",
        "/profile/*",
        "/orders/*",
        "/payments/*",
        "/saved-properties/*",
        "/applications/*"
})
public class AuthenticationFilter implements Filter {

    // Public resources that don't require authentication
    private static final Set<String> PUBLIC_RESOURCES = new HashSet<>(Arrays.asList(
            "/css/", "/js/", "/images/", "/fonts/", "/favicon.ico"));

    // Pages that should redirect back after login
    private static final Set<String> REDIRECT_AFTER_LOGIN = new HashSet<>(Arrays.asList(
            "/checkout", "/saved-properties", "/applications"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the request is for a public resource
        String requestPath = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (isPublicResource(requestPath)) {
            // Allow access to public resources
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in
        if (SessionUtil.isLoggedIn(httpRequest)) {
            // User is logged in, continue with the request
            chain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to login page

            // Check if we should remember the original request for redirect after login
            if (shouldRedirectAfterLogin(requestPath)) {
                // Store the original request URL in session
                HttpSession session = httpRequest.getSession(true);
                session.setAttribute("redirectAfterLogin", httpRequest.getRequestURI());
            }

            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    /**
     * Check if the requested resource is public and doesn't require authentication
     *
     * @param path Request path
     * @return true if public resource, false otherwise
     */
    private boolean isPublicResource(String path) {
        return PUBLIC_RESOURCES.stream().anyMatch(path::startsWith);
    }

    /**
     * Check if the page should redirect back after login
     *
     * @param path Request path
     * @return true if should redirect after login, false otherwise
     */
    private boolean shouldRedirectAfterLogin(String path) {
        return REDIRECT_AFTER_LOGIN.stream().anyMatch(path::startsWith);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
