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
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        </head>

        <body>
            <jsp:include page="header.jsp" />

            <main>
                <section class="dashboard-section">
                    <div class="container">
                        <h1 class="dashboard-title">Admin Dashboard</h1>

                        <div class="dashboard-container">
                            <jsp:include page="admin-sidebar.jsp" />
                            <div class="dashboard-content">
                                <div class="dashboard-header">
                                    <h2>Admin Dashboard</h2>
                                </div>

                                <div class="admin-stats">
                                    <div class="stat-card">
                                        <div class="stat-icon">
                                            <i class="fas fa-users"></i>
                                        </div>
                                        <h3>Total Users</h3>
                                        <p class="stat-value">3</p>
                                        <p class="stat-change positive">
                                            <i class="fas fa-arrow-up"></i> 10% from last month
                                        </p>
                                    </div>
                                    <div class="stat-card">
                                        <div class="stat-icon">
                                            <i class="fas fa-home"></i>
                                        </div>
                                        <h3>Total Properties</h3>
                                        <p class="stat-value">3</p>
                                        <p class="stat-change positive">
                                            <i class="fas fa-arrow-up"></i> 5% from last month
                                        </p>
                                    </div>
                                    <div class="stat-card">
                                        <div class="stat-icon">
                                            <i class="fas fa-file-alt"></i>
                                        </div>
                                        <h3>Total Applications</h3>
                                        <p class="stat-value">0</p>
                                        <p class="stat-change">
                                            No change from last month
                                        </p>
                                    </div>
                                    <div class="stat-card">
                                        <div class="stat-icon">
                                            <i class="fas fa-star"></i>
                                        </div>
                                        <h3>Total Reviews</h3>
                                        <p class="stat-value">0</p>
                                        <p class="stat-change">
                                            No change from last month
                                        </p>
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