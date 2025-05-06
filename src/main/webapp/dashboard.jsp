<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Dashboard - Ghar Dalali</title>
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
        </head>

        <body>
            <jsp:include page="header.jsp" />

            <main>
                <section class="dashboard-section">
                    <div class="container">
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
                                                href="${pageContext.request.contextPath}/dashboard/profile">My
                                                Profile</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/saved-properties">Saved
                                                Properties</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/applications">My
                                                Applications</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/reviews">My
                                                Reviews</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/payment-history">Payment
                                                History</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/change-password">Change
                                                Password</a></li>
                                    </ul>
                                </nav>
                            </div>
                            <div class="dashboard-content">
                                <div class="dashboard-header">
                                    <h2>My Profile</h2>
                                    <button class="btn">View Full Profile</button>
                                </div>
                                <div class="profile-details">
                                    <div class="profile-item">
                                        <h4>First Name</h4>
                                        <p>${user.firstName}</p>
                                    </div>
                                    <div class="profile-item">
                                        <h4>Last Name</h4>
                                        <p>${user.lastName}</p>
                                    </div>
                                    <div class="profile-item">
                                        <h4>Email Address</h4>
                                        <p>${user.email}</p>
                                    </div>
                                    <div class="profile-item">
                                        <h4>Phone Number</h4>
                                        <p>${user.contactNumber}</p>
                                    </div>
                                    <div class="profile-item">
                                        <h4>Address</h4>
                                        <p>${user.address}</p>
                                    </div>
                                    <div class="profile-item">
                                        <h4>Member Since</h4>
                                        <p>${user.createdAt}</p>
                                    </div>
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