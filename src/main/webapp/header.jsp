<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <!-- Include custom styling -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/color-palette.css?v=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar-custom.css?v=1.0">

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
                            <c:choose>
                                <c:when test="${sessionScope.user.userRole eq 'ADMIN'}">
                                    <a href="${pageContext.request.contextPath}/admin/dashboard"
                                        class="btn btn-outline">Dashboard</a>
                                    <a href="${pageContext.request.contextPath}/logout"
                                        class="btn btn-primary">Logout</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline">My
                                        Profile</a>
                                    <a href="${pageContext.request.contextPath}/logout"
                                        class="btn btn-primary">Logout</a>
                                </c:otherwise>
                            </c:choose>
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