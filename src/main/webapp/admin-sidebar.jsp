<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

        <div class="dashboard-sidebar">
            <div class="user-info">
                <div class="user-avatar">
                    <img src="${user.profileImagePath != null ? user.profileImagePath : pageContext.request.contextPath.concat('/images/users/default-profile.jpg')}"
                        alt="User Avatar">
                </div>
                <div class="user-details">
                    <h3>${user.fullName}</h3>
                    <p>${user.email}</p>
                </div>
            </div>
            <nav class="dashboard-nav">
                <ul>
                    <li class="${activeTab == 'profile' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/profile">
                            <i class="fas fa-user"></i> My Profile
                        </a>
                    </li>
                    <li class="${activeTab == 'dashboard' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </a>
                    </li>
                    <li class="${activeTab == 'users' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/users">
                            <i class="fas fa-users"></i> Manage Users
                        </a>
                    </li>
                    <li class="${activeTab == 'properties' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/properties">
                            <i class="fas fa-home"></i> Manage Properties
                        </a>
                    </li>
                    <li class="${activeTab == 'applications' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/applications">
                            <i class="fas fa-file-alt"></i> Manage Applications
                        </a>
                    </li>
                    <li class="${activeTab == 'reviews' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/reviews">
                            <i class="fas fa-star"></i> Manage Reviews
                        </a>
                    </li>
                    <li class="${activeTab == 'payments' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/payments">
                            <i class="fas fa-money-bill-wave"></i> Payment History
                        </a>
                    </li>
                    <li class="${activeTab == 'change-password' ? 'active' : ''}">
                        <a href="${pageContext.request.contextPath}/admin/change-password">
                            <i class="fas fa-key"></i> Change Password
                        </a>
                    </li>
                    <li class="logout-item">
                        <a href="${pageContext.request.contextPath}/logout">
                            <i class="fas fa-sign-out-alt"></i> Logout
                        </a>
                    </li>
                </ul>
            </nav>
        </div>