<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>My Profile - Admin Dashboard - Ghar Dalali</title>
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
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/property-forms.css">
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
                                    <h2>My Profile</h2>
                                    <p>Manage your personal information</p>
                                </div>

                                <div class="form-container">
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-error">
                                            <p>${errorMessage}</p>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty successMessage}">
                                        <div class="alert alert-success">
                                            <p>${successMessage}</p>
                                        </div>
                                    </c:if>

                                    <div class="profile-overview">
                                        <div class="profile-avatar-large">
                                            <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg"
                                                alt="Profile Avatar">
                                            <button class="change-avatar-btn" title="Change Profile Picture">
                                                <i class="fas fa-camera"></i>
                                            </button>
                                        </div>
                                        <div class="profile-details">
                                            <h3>${user.fullName}</h3>
                                            <p><i class="fas fa-envelope"></i> ${user.email}</p>
                                            <p><i class="fas fa-phone"></i> ${user.contactNumber}</p>
                                            <p><i class="fas fa-map-marker-alt"></i> ${user.address}</p>
                                            <p><i class="fas fa-calendar-alt"></i> Member since ${user.createdAt}</p>
                                            <p><i class="fas fa-user-shield"></i> Role: Administrator</p>
                                        </div>
                                    </div>

                                    <form id="profile-form" class="property-form"
                                        action="${pageContext.request.contextPath}/admin/profile" method="post">
                                        <!-- CSRF Protection -->
                                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                        <h3>Edit Profile Information</h3>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="firstName">First Name*</label>
                                                <input type="text" id="firstName" name="firstName"
                                                    value="${user.firstName}" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="lastName">Last Name*</label>
                                                <input type="text" id="lastName" name="lastName"
                                                    value="${user.lastName}" required>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="email">Email Address*</label>
                                            <input type="email" id="email" name="email" value="${user.email}" readonly>
                                            <small>Email address cannot be changed</small>
                                        </div>

                                        <div class="form-group">
                                            <label for="contactNumber">Contact Number</label>
                                            <input type="tel" id="contactNumber" name="contactNumber"
                                                value="${user.contactNumber}">
                                        </div>

                                        <div class="form-group">
                                            <label for="address">Address</label>
                                            <textarea id="address" name="address" rows="3">${user.address}</textarea>
                                        </div>

                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary">Update Profile</button>
                                            <a href="${pageContext.request.contextPath}/admin/dashboard"
                                                class="btn btn-secondary">Cancel</a>
                                        </div>
                                    </form>

                                    <div class="profile-actions">
                                        <a href="${pageContext.request.contextPath}/admin/change-password"
                                            class="btn btn-outline">
                                            <i class="fas fa-key"></i> Change Password
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/components-updated.js" defer></script>
            <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
        </body>

        </html>