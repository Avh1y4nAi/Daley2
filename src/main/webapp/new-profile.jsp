<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>My Profile | Ghar Dalali</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        </head>

        <body>
            <jsp:include page="header.jsp" />

            <main>
                <section class="profile-section">
                    <div class="container">
                        <div class="profile-container">
                            <!-- Profile Header -->
                            <div class="profile-header">
                                <div class="profile-cover">
                                    <img src="${pageContext.request.contextPath}/images/cover-placeholder.jpg"
                                        alt="Cover Image">
                                    <div class="cover-overlay"></div>
                                </div>
                                <div class="profile-header-content">
                                    <div class="profile-avatar">
                                        <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg"
                                            alt="Profile Image">
                                        <button class="change-avatar-btn" title="Change Profile Picture">
                                            <i class="fas fa-camera"></i>
                                        </button>
                                    </div>
                                    <div class="profile-info">
                                        <h1>${user.fullName}</h1>
                                        <p>Member since ${user.createdAt}</p>
                                    </div>
                                    <div class="profile-actions">
                                        <button id="edit-profile-btn" class="btn btn-primary">Edit Profile</button>
                                    </div>
                                </div>
                            </div>

                            <!-- Success/Error Messages -->
                            <c:if test="${not empty successMessage}">
                                <div class="alert alert-success">
                                    ${successMessage}
                                </div>
                            </c:if>

                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-error">
                                    ${errorMessage}
                                </div>
                            </c:if>

                            <!-- Profile Content -->
                            <div class="profile-content">
                                <!-- View Profile Section -->
                                <div id="view-profile" class="profile-section-content active">
                                    <!-- Personal Information Card -->
                                    <div class="profile-card">
                                        <div class="card-header">
                                            <h2>Personal Information</h2>
                                        </div>
                                        <div class="card-body">
                                            <div class="info-grid">
                                                <div class="info-item">
                                                    <label>First Name</label>
                                                    <p>${user.firstName}</p>
                                                </div>
                                                <div class="info-item">
                                                    <label>Last Name</label>
                                                    <p>${user.lastName}</p>
                                                </div>
                                                <div class="info-item">
                                                    <label>Email Address</label>
                                                    <p>${user.email}</p>
                                                </div>
                                                <div class="info-item">
                                                    <label>Phone Number</label>
                                                    <p>${user.contactNumber}</p>
                                                </div>
                                                <div class="info-item">
                                                    <label>Address</label>
                                                    <p>${user.address}</p>
                                                </div>
                                                <div class="info-item">
                                                    <label>City</label>
                                                    <p>Kathmandu</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Account Security Card -->
                                    <div class="profile-card">
                                        <div class="card-header">
                                            <h2>Account Security</h2>
                                        </div>
                                        <div class="card-body">
                                            <div class="security-options">
                                                <div class="security-item">
                                                    <div class="security-info">
                                                        <h3>Password</h3>
                                                        <p>Last changed 3 months ago</p>
                                                    </div>
                                                    <button id="change-password-btn" class="btn btn-secondary">Change
                                                        Password</button>
                                                </div>
                                                <div class="security-item">
                                                    <div class="security-info">
                                                        <h3>Two-Factor Authentication</h3>
                                                        <p>Not enabled</p>
                                                    </div>
                                                    <button id="enable-2fa-btn"
                                                        class="btn btn-secondary">Enable</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Preferences Card -->
                                    <div class="profile-card">
                                        <div class="card-header">
                                            <h2>Preferences</h2>
                                        </div>
                                        <div class="card-body">
                                            <div class="preferences-options">
                                                <div class="preference-item">
                                                    <div class="preference-info">
                                                        <h3>Email Notifications</h3>
                                                        <p>Receive emails about new properties, updates, and promotions
                                                        </p>
                                                    </div>
                                                    <label class="toggle-switch">
                                                        <input type="checkbox" checked>
                                                        <span class="toggle-slider"></span>
                                                    </label>
                                                </div>
                                                <div class="preference-item">
                                                    <div class="preference-info">
                                                        <h3>SMS Notifications</h3>
                                                        <p>Receive text messages about property updates and appointments
                                                        </p>
                                                    </div>
                                                    <label class="toggle-switch">
                                                        <input type="checkbox">
                                                        <span class="toggle-slider"></span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Edit Profile Section -->
                                <div id="edit-profile" class="profile-section-content">
                                    <div class="profile-card">
                                        <div class="card-header">
                                            <h2>Edit Personal Information</h2>
                                        </div>
                                        <div class="card-body">
                                            <form id="profile-form" action="${pageContext.request.contextPath}/profile"
                                                method="post">
                                                <!-- CSRF Token -->
                                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                                <div class="form-grid">
                                                    <div class="form-group">
                                                        <label for="firstName">First Name <span
                                                                class="required">*</span></label>
                                                        <input type="text" id="firstName" name="firstName"
                                                            value="${firstName != null ? firstName : user.firstName}"
                                                            required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="lastName">Last Name <span
                                                                class="required">*</span></label>
                                                        <input type="text" id="lastName" name="lastName"
                                                            value="${lastName != null ? lastName : user.lastName}"
                                                            required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="email">Email Address</label>
                                                        <input type="email" id="email" name="email"
                                                            value="${user.email}" disabled>
                                                        <small>Email cannot be changed</small>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="contactNumber">Phone Number <span
                                                                class="required">*</span></label>
                                                        <input type="tel" id="contactNumber" name="contactNumber"
                                                            value="${contactNumber != null ? contactNumber : user.contactNumber}"
                                                            required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="address">Address <span
                                                                class="required">*</span></label>
                                                        <input type="text" id="address" name="address"
                                                            value="${address != null ? address : user.address}"
                                                            required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="city">City <span class="required">*</span></label>
                                                        <input type="text" id="city" name="city" value="Kathmandu"
                                                            required>
                                                    </div>
                                                </div>
                                                <div class="form-actions">
                                                    <button type="button" id="cancel-edit-btn"
                                                        class="btn btn-secondary">Cancel</button>
                                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                                <!-- Change Password Section -->
                                <div id="change-password" class="profile-section-content">
                                    <div class="profile-card">
                                        <div class="card-header">
                                            <h2>Change Password</h2>
                                        </div>
                                        <div class="card-body">
                                            <form id="password-form"
                                                action="${pageContext.request.contextPath}/profile/change-password"
                                                method="post">
                                                <!-- CSRF Token -->
                                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                                <div class="form-group">
                                                    <label for="current-password">Current Password <span
                                                            class="required">*</span></label>
                                                    <input type="password" id="current-password" name="currentPassword"
                                                        required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="new-password">New Password <span
                                                            class="required">*</span></label>
                                                    <input type="password" id="new-password" name="newPassword"
                                                        required>
                                                    <div class="password-strength">
                                                        <div class="strength-meter">
                                                            <div class="strength-segment"></div>
                                                            <div class="strength-segment"></div>
                                                            <div class="strength-segment"></div>
                                                            <div class="strength-segment"></div>
                                                        </div>
                                                        <p class="strength-text">Password Strength: <span>Very
                                                                Weak</span></p>
                                                    </div>
                                                    <div class="password-requirements">
                                                        <p>Password must contain:</p>
                                                        <ul>
                                                            <li>At least 8 characters</li>
                                                            <li>At least one uppercase letter</li>
                                                            <li>At least one number</li>
                                                            <li>At least one special character</li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="confirm-password">Confirm New Password <span
                                                            class="required">*</span></label>
                                                    <input type="password" id="confirm-password" name="confirmPassword"
                                                        required>
                                                </div>
                                                <div class="form-actions">
                                                    <button type="button" id="cancel-password-btn"
                                                        class="btn btn-secondary">Cancel</button>
                                                    <button type="submit" class="btn btn-primary">Change
                                                        Password</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
            <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        </body>

        </html>