<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!-- Navigation Bar -->
        <header class="navbar">
            <div class="container">
                <div class="logo">
                    <a href="${pageContext.request.contextPath}/" class="home-link">
                        <h1>Ghar Dalali</h1>
                    </a>
                </div>
                <nav class="nav-menu">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/" id="nav-home" class="home-link">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/properties" id="nav-properties">Properties</a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/about" id="nav-about">About</a></li>
                        <li><a href="${pageContext.request.contextPath}/contact" id="nav-contact">Contact</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                            <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline">Dashboard</a>
                            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary">Logout</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="mobile-menu-toggle">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
        </header>