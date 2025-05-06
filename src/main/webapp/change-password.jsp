<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password - Ghar Dalali</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <!-- Dashboard Section -->
    <section class="dashboard-section">
        <div class="container">
            <div class="dashboard-container">
                <!-- Dashboard Sidebar -->
                <div class="dashboard-sidebar">
                    <div class="user-profile">
                        <div class="user-avatar">
                            <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg" alt="User Avatar">
                        </div>
                        <div class="user-info">
                            <h3>${user.fullName}</h3>
                            <p>${user.email}</p>
                        </div>
                    </div>
                    <nav class="dashboard-nav">
                        <ul>
                            <li>
                                <a href="${pageContext.request.contextPath}/dashboard">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                                    </svg>
                                    Dashboard
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/dashboard/saved-properties">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
                                    </svg>
                                    Saved Properties
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/dashboard/applications">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                        <polyline points="14 2 14 8 20 8"></polyline>
                                        <line x1="16" y1="13" x2="8" y2="13"></line>
                                        <line x1="16" y1="17" x2="8" y2="17"></line>
                                        <polyline points="10 9 9 9 8 9"></polyline>
                                    </svg>
                                    Applications
                                </a>
                            </li>
                            <li class="active">
                                <a href="${pageContext.request.contextPath}/dashboard/change-password">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                                        <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                                    </svg>
                                    Change Password
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/logout">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                                        <polyline points="16 17 21 12 16 7"></polyline>
                                        <line x1="21" y1="12" x2="9" y2="12"></line>
                                    </svg>
                                    Logout
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
                
                <!-- Dashboard Content -->
                <div class="dashboard-content">
                    <div class="dashboard-header">
                        <h2>Change Password</h2>
                        <p>Update your password to keep your account secure</p>
                    </div>
                    
                    <c:if test="${not empty errorMessage}">
                        <div class="error-message">
                            <p>${errorMessage}</p>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty successMessage}">
                        <div class="success-message">
                            <p>${successMessage}</p>
                        </div>
                    </c:if>
                    
                    <div class="dashboard-card">
                        <form id="password-form" class="password-form" action="${pageContext.request.contextPath}/dashboard/change-password" method="post">
                            <div class="form-group">
                                <label for="current-password">Current Password</label>
                                <input type="password" id="current-password" name="current-password" required>
                            </div>
                            <div class="form-group">
                                <label for="new-password">New Password</label>
                                <input type="password" id="new-password" name="new-password" required>
                                <div class="password-strength">
                                    <div class="strength-meter">
                                        <div class="strength-segment"></div>
                                        <div class="strength-segment"></div>
                                        <div class="strength-segment"></div>
                                        <div class="strength-segment"></div>
                                    </div>
                                    <p class="strength-text">Password strength: <span id="strength-value">Weak</span></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirm-password">Confirm New Password</label>
                                <input type="password" id="confirm-password" name="confirm-password" required>
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
                                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-outline">Cancel</a>
                                <button type="submit" class="btn btn-primary">Update Password</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <jsp:include page="footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // Simple password strength checker
        document.getElementById('new-password').addEventListener('input', function() {
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
            strengthValue.textContent = getStrengthText(strength);
            strengthValue.style.color = getStrengthColor(strength);
        });
        
        function getStrengthText(strength) {
            switch (strength) {
                case 0:
                case 1:
                    return 'Weak';
                case 2:
                    return 'Fair';
                case 3:
                    return 'Good';
                case 4:
                    return 'Strong';
                default:
                    return 'Weak';
            }
        }
        
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
        document.getElementById('password-form').addEventListener('submit', function(e) {
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
