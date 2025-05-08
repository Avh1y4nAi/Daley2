package com.ghardalali.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling about page
 */
@WebServlet("/about")
public class AboutServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pre-process request
        preProcessRequest(request, response);

        // Set active navigation tab
        setActiveTab(request, "about");

        // Forward to about page
        request.getRequestDispatcher("/about.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pre-process request
        preProcessRequest(request, response);

        // Just delegate to doGet
        doGet(request, response);
    }
}
