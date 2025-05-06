<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Change Password - Admin Dashboard - Ghar Dalali</title>
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
                                    <h2>Change Password</h2>
                                    <p>Update your password to keep your account secure</p>
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

                                    <form id="password-form" class="property-form"
                                        action="${pageContext.request.contextPath}/admin/change-password" method="post">
                                        <!-- CSRF Protection -->
                                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                        <div class="form-group">
                                            <label for="current-password">Current Password*</label>
                                            <input type="password" id="current-password" name="currentPassword"
                                                required>
                                        </div>

                                        <div class="form-group">
                                            <label for="new-password">New Password*</label>
                                            <input type="password" id="new-password" name="newPassword" required>
                                            <div class="password-strength">
                                                <div class="strength-meter">
                                                    <div class="strength-segment"></div>
                                                    <div class="strength-segment"></div>
                                                    <div class="strength-segment"></div>
                                                    <div class="strength-segment"></div>
                                                </div>
                                                <span id="strength-value">Weak</span>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="confirm-password">Confirm New Password*</label>
                                            <input type="password" id="confirm-password" name="confirmPassword"
                                                required>
                                        </div>

                                        <div class="password-requirements">
                                            <p>Password must:</p>
                                            <ul>
                                                <li>Be at least 8 characters long</li>
                                                <li>Include at least one uppercase letter</li>
                                                <li>Include at least one number</li>
                                                <li>Include at least one special character</li>
                                            </ul>
                                        </div>

                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary">Update Password</button>
                                            <a href="${pageContext.request.contextPath}/admin/dashboard"
                                                class="btn btn-secondary">Cancel</a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/components-updated.js" defer></script>
            <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
            <script>
                // Simple password strength checker
                document.getElementById('new-password').addEventListener('input', function () {
                    const password = this.value;
                    const strengthValue = document.getElementById('strength-value');
                    const strengthSegments = document.querySelectorAll('.strength-segment');

                    // Reset segments
                    strengthSegments.forEach(segment => {
                        segment.style.backgroundColor = '#e0e0e0';
                    });

                    if (password.length === 0) {
                        strengthValue.textContent = 'Weak';
                        return;
                    }

                    let strength = 0;

                    // Length check
                    if (password.length >= 8) {
                        strength += 1;
                    }

                    // Uppercase check
                    if (/[A-Z]/.test(password)) {
                        strength += 1;
                    }

                    // Number check
                    if (/[0-9]/.test(password)) {
                        strength += 1;
                    }

                    // Special character check
                    if (/[^A-Za-z0-9]/.test(password)) {
                        strength += 1;
                    }

                    // Update strength meter
                    for (let i = 0; i < strength; i++) {
                        strengthSegments[i].style.backgroundColor = getStrengthColor(strength);
                    }

                    // Update strength text
                    switch (strength) {
                        case 0:
                        case 1:
                            strengthValue.textContent = 'Weak';
                            break;
                        case 2:
                            strengthValue.textContent = 'Fair';
                            break;
                        case 3:
                            strengthValue.textContent = 'Good';
                            break;
                        case 4:
                            strengthValue.textContent = 'Strong';
                            break;
                    }
                });

                function getStrengthColor(strength) {
                    switch (strength) {
                        case 0:
                        case 1:
                            return '#ff4d4d'; // Red
                        case 2:
                            return '#ffa64d'; // Orange
                        case 3:
                            return '#4da6ff'; // Blue
                        case 4:
                            return '#4dff4d'; // Green
                        default:
                            return '#e0e0e0'; // Gray
                    }
                }

                // Confirm password validation
                document.getElementById('password-form').addEventListener('submit', function (e) {
                    const newPassword = document.getElementById('new-password').value;
                    const confirmPassword = document.getElementById('confirm-password').value;

                    if (newPassword !== confirmPassword) {
                        e.preventDefault();
                        alert('New password and confirm password do not match');
                    }
                });
            </script>
        </body>

        </html>