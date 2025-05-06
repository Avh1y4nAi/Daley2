<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Admin Dashboard - Ghar Dalali</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
        </head>

        <body>
            <jsp:include page="header.jsp" />

            <main>
                <section class="dashboard-section">
                    <div class="container">
                        <h1 class="dashboard-title">Admin Dashboard</h1>

                        <div class="dashboard-container">
                            <div class="dashboard-sidebar">
                                <div class="user-info">
                                    <div class="user-avatar">
                                        <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg"
                                            alt="User Avatar">
                                    </div>
                                    <div class="user-details">
                                        <h3>${user.fullName}</h3>
                                        <p>${user.email}</p>
                                    </div>
                                </div>
                                <nav class="dashboard-nav">
                                    <ul>
                                        <li class="active"><a
                                                href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/admin/properties">Manage
                                                Properties</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/applications">Manage
                                                Applications</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/reviews">Manage
                                                Reviews</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/payments">Payment
                                                History</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/change-password">Change
                                                Password</a></li>
                                    </ul>
                                </nav>
                            </div>
                            <div class="dashboard-content">
                                <div class="dashboard-header">
                                    <h2>Admin Dashboard</h2>
                                </div>

                                <div class="admin-stats">
                                    <div class="stat-card">
                                        <h3>Total Users</h3>
                                        <p class="stat-value">3</p>
                                    </div>
                                    <div class="stat-card">
                                        <h3>Total Properties</h3>
                                        <p class="stat-value">3</p>
                                    </div>
                                    <div class="stat-card">
                                        <h3>Total Applications</h3>
                                        <p class="stat-value">0</p>
                                    </div>
                                    <div class="stat-card">
                                        <h3>Total Reviews</h3>
                                        <p class="stat-value">0</p>
                                    </div>
                                </div>

                                <div class="recent-activity">
                                    <h3>Recent Activity</h3>
                                    <p>Welcome to the admin dashboard. From here you can manage all aspects of the Ghar
                                        Dalali application.</p>
                                    <p>Use the navigation menu on the left to access different management sections.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
        </body>

        </html>