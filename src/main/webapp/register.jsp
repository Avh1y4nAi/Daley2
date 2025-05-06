<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Register - Ghar Dalali</title>
            <link rel="preconnect" href="https://fonts.googleapis.com">
            <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
        </head>

        <body>
            <jsp:include page="header.jsp" />

            <!-- Register Section -->
            <section class="auth-section">
                <div class="container">
                    <div class="auth-container">
                        <div class="auth-content">
                            <h2>Create an Account</h2>
                            <p>Join Ghar Dalali to find your dream property or list your own.</p>

                            <c:if test="${not empty errorMessage}">
                                <div class="error-message">
                                    <p>${errorMessage}</p>
                                </div>
                            </c:if>

                            <div class="auth-social">
                                <p>Register with</p>
                                <div class="social-buttons">
                                    <button type="button" class="social-btn google">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                            viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                            stroke-linecap="round" stroke-linejoin="round">
                                            <path
                                                d="M20.283 10.356h-8.327v3.451h4.792c-.446 2.193-2.313 3.453-4.792 3.453a5.27 5.27 0 0 1-5.279-5.28 5.27 5.27 0 0 1 5.279-5.279c1.259 0 2.397.447 3.29 1.178l2.6-2.599c-1.584-1.381-3.615-2.233-5.89-2.233a8.908 8.908 0 0 0-8.934 8.934 8.907 8.907 0 0 0 8.934 8.934c4.467 0 8.529-3.249 8.529-8.934 0-.528-.081-1.097-.202-1.625z">
                                            </path>
                                        </svg>
                                        Google
                                    </button>
                                    <button type="button" class="social-btn facebook">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                                            viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                            stroke-linecap="round" stroke-linejoin="round">
                                            <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z">
                                            </path>
                                        </svg>
                                        Facebook
                                    </button>
                                </div>
                            </div>

                            <div class="auth-divider">
                                <span>or</span>
                            </div>

                            <form class="auth-form" action="${pageContext.request.contextPath}/register" method="post">
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="firstName">First Name</label>
                                        <input type="text" id="firstName" name="firstName"
                                            placeholder="Enter your first name" value="${firstName}" required>
                                        <c:if test="${not empty firstNameError}">
                                            <span class="error">${firstNameError}</span>
                                        </c:if>
                                    </div>

                                    <div class="form-group">
                                        <label for="lastName">Last Name</label>
                                        <input type="text" id="lastName" name="lastName"
                                            placeholder="Enter your last name" value="${lastName}" required>
                                        <c:if test="${not empty lastNameError}">
                                            <span class="error">${lastNameError}</span>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="email">Email Address</label>
                                    <input type="email" id="email" name="email" placeholder="Enter your email"
                                        value="${email}" required>
                                    <c:if test="${not empty emailError}">
                                        <span class="error">${emailError}</span>
                                    </c:if>
                                </div>

                                <div class="form-group">
                                    <label for="contactNumber">Contact Number</label>
                                    <input type="tel" id="contactNumber" name="contactNumber"
                                        placeholder="Enter your phone number" value="${contactNumber}" required>
                                    <c:if test="${not empty contactNumberError}">
                                        <span class="error">${contactNumberError}</span>
                                    </c:if>
                                </div>

                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" id="password" name="password" placeholder="Create a password"
                                        required>
                                    <c:if test="${not empty passwordError}">
                                        <span class="error">${passwordError}</span>
                                    </c:if>
                                </div>

                                <div class="form-group">
                                    <label for="confirmPassword">Confirm Password</label>
                                    <input type="password" id="confirmPassword" name="confirmPassword"
                                        placeholder="Confirm your password" required>
                                    <c:if test="${not empty confirmPasswordError}">
                                        <span class="error">${confirmPasswordError}</span>
                                    </c:if>
                                </div>

                                <div class="form-options">
                                    <div class="terms-agreement">
                                        <input type="checkbox" id="termsAgreed" name="termsAgreed" required>
                                        <label for="termsAgreed">I agree to the <a
                                                href="${pageContext.request.contextPath}/terms">Terms of Service</a> and
                                            <a href="${pageContext.request.contextPath}/privacy">Privacy
                                                Policy</a></label>
                                        <c:if test="${not empty termsError}">
                                            <span class="error">${termsError}</span>
                                        </c:if>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-primary btn-block">Register</button>
                            </form>

                            <div class="auth-footer">
                                <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a>
                                </p>
                            </div>
                        </div>

                        <div class="auth-image">
                            <img src="${pageContext.request.contextPath}/images/register-bg.jpg" alt="Register"
                                loading="lazy">
                        </div>
                    </div>
                </div>
            </section>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
            <script>
                // Client-side validation for registration form
                document.addEventListener('DOMContentLoaded', function () {
                    const form = document.querySelector('.auth-form');
                    const password = document.getElementById('password');
                    const confirmPassword = document.getElementById('confirmPassword');
                    const email = document.getElementById('email');
                    const contactNumber = document.getElementById('contactNumber');

                    // Password strength indicator
                    password.addEventListener('input', function () {
                        const value = this.value;
                        let strength = 0;
                        let message = '';

                        // Check length
                        if (value.length >= 8) {
                            strength += 1;
                        }

                        // Check uppercase
                        if (/[A-Z]/.test(value)) {
                            strength += 1;
                        }

                        // Check numbers
                        if (/[0-9]/.test(value)) {
                            strength += 1;
                        }

                        // Check special characters
                        if (/[^A-Za-z0-9]/.test(value)) {
                            strength += 1;
                        }

                        // Set message based on strength
                        switch (strength) {
                            case 0:
                            case 1:
                                message = 'Weak';
                                color = '#ff4d4d';
                                break;
                            case 2:
                                message = 'Fair';
                                color = '#ffa64d';
                                break;
                            case 3:
                                message = 'Good';
                                color = '#4da6ff';
                                break;
                            case 4:
                                message = 'Strong';
                                color = '#4dff4d';
                                break;
                        }

                        // Show strength indicator if not already present
                        let strengthIndicator = this.nextElementSibling;
                        if (!strengthIndicator || !strengthIndicator.classList.contains('password-strength')) {
                            strengthIndicator = document.createElement('div');
                            strengthIndicator.className = 'password-strength';
                            strengthIndicator.innerHTML = '<span>Password strength: </span><span class="strength-value"></span>';
                            this.parentNode.insertBefore(strengthIndicator, this.nextSibling);
                        }

                        // Update strength indicator
                        const strengthValue = strengthIndicator.querySelector('.strength-value');
                        strengthValue.textContent = message;
                        strengthValue.style.color = color;
                    });

                    // Real-time password match validation
                    confirmPassword.addEventListener('input', function () {
                        if (password.value !== this.value) {
                            this.setCustomValidity('Passwords do not match');
                        } else {
                            this.setCustomValidity('');
                        }
                    });

                    // Email validation
                    email.addEventListener('input', function () {
                        const emailRegex = /^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/;
                        if (!emailRegex.test(this.value) && this.value.length > 0) {
                            this.setCustomValidity('Please enter a valid email address');
                        } else {
                            this.setCustomValidity('');
                        }
                    });

                    // Contact number validation
                    contactNumber.addEventListener('input', function () {
                        const numberRegex = /^[0-9]{10}$/;
                        if (!numberRegex.test(this.value) && this.value.length > 0) {
                            this.setCustomValidity('Please enter a valid 10-digit contact number');
                        } else {
                            this.setCustomValidity('');
                        }
                    });

                    // Form submission validation
                    form.addEventListener('submit', function (e) {
                        // Validate password match
                        if (password.value !== confirmPassword.value) {
                            e.preventDefault();
                            alert('Passwords do not match');
                            return false;
                        }

                        // Validate terms agreement
                        const termsAgreed = document.getElementById('termsAgreed');
                        if (!termsAgreed.checked) {
                            e.preventDefault();
                            alert('You must agree to the terms and conditions');
                            return false;
                        }

                        return true;
                    });
                });
            </script>
        </body>

        </html>