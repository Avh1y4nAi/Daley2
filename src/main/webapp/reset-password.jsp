<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password - Ghar Dalali</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .password-strength-meter {
            height: 5px;
            width: 100%;
            background-color: #ddd;
            margin-top: 5px;
            border-radius: 3px;
            position: relative;
        }
        
        .password-strength-meter-fill {
            height: 100%;
            border-radius: 3px;
            transition: width 0.3s ease;
        }
        
        .strength-text {
            font-size: 12px;
            margin-top: 5px;
        }
        
        .very-weak { width: 20%; background-color: #f00; }
        .weak { width: 40%; background-color: #ff8c00; }
        .medium { width: 60%; background-color: #ffcc00; }
        .strong { width: 80%; background-color: #9bc158; }
        .very-strong { width: 100%; background-color: #0f9d58; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main>
            <div class="auth-container">
                <div class="auth-card">
                    <div class="auth-header">
                        <h2>Reset Your Password</h2>
                        <p>Please enter your new password below.</p>
                    </div>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                        </div>
                    </c:if>

                    <form class="auth-form" action="${pageContext.request.contextPath}/reset-password" method="post">
                        <!-- CSRF Protection -->
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                        
                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" placeholder="Enter your new password" required>
                            <div class="password-strength-meter">
                                <div class="password-strength-meter-fill" id="password-strength-meter-fill"></div>
                            </div>
                            <div class="strength-text" id="password-strength-text"></div>
                            <c:if test="${not empty newPasswordError}">
                                <span class="error">${newPasswordError}</span>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your new password" required>
                            <c:if test="${not empty confirmPasswordError}">
                                <span class="error">${confirmPasswordError}</span>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block">Reset Password</button>
                        </div>
                    </form>

                    <div class="auth-footer">
                        <p>Remember your password? <a href="${pageContext.request.contextPath}/login">Login</a></p>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="footer.jsp" />
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const passwordInput = document.getElementById('newPassword');
            const confirmPasswordInput = document.getElementById('confirmPassword');
            const strengthMeter = document.getElementById('password-strength-meter-fill');
            const strengthText = document.getElementById('password-strength-text');
            
            passwordInput.addEventListener('input', function() {
                const password = passwordInput.value;
                const strength = calculatePasswordStrength(password);
                
                // Update strength meter
                strengthMeter.className = 'password-strength-meter-fill';
                
                if (password.length === 0) {
                    strengthMeter.style.width = '0%';
                    strengthText.textContent = '';
                } else if (strength === 0) {
                    strengthMeter.classList.add('very-weak');
                    strengthText.textContent = 'Very Weak';
                    strengthText.style.color = '#f00';
                } else if (strength === 1) {
                    strengthMeter.classList.add('weak');
                    strengthText.textContent = 'Weak';
                    strengthText.style.color = '#ff8c00';
                } else if (strength === 2) {
                    strengthMeter.classList.add('medium');
                    strengthText.textContent = 'Medium';
                    strengthText.style.color = '#ffcc00';
                } else if (strength === 3) {
                    strengthMeter.classList.add('strong');
                    strengthText.textContent = 'Strong';
                    strengthText.style.color = '#9bc158';
                } else {
                    strengthMeter.classList.add('very-strong');
                    strengthText.textContent = 'Very Strong';
                    strengthText.style.color = '#0f9d58';
                }
            });
            
            confirmPasswordInput.addEventListener('input', function() {
                if (passwordInput.value !== confirmPasswordInput.value) {
                    confirmPasswordInput.setCustomValidity("Passwords don't match");
                } else {
                    confirmPasswordInput.setCustomValidity('');
                }
            });
            
            function calculatePasswordStrength(password) {
                let strength = 0;
                
                if (password.length >= 8) {
                    strength += 1;
                }
                
                if (password.match(/[A-Z]/)) {
                    strength += 1;
                }
                
                if (password.match(/[0-9]/)) {
                    strength += 1;
                }
                
                if (password.match(/[^A-Za-z0-9]/)) {
                    strength += 1;
                }
                
                return strength;
            }
        });
    </script>
</body>
</html>
