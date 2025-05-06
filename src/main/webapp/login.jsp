<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Login - Ghar Dalali</title>
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

            <!-- Login Section -->
            <section class="auth-section">
                <div class="container">
                    <div class="auth-container">
                        <div class="auth-content">
                            <h2>Login to Your Account</h2>
                            <p>Welcome back! Please enter your credentials to access your account.</p>

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

                            <div class="auth-social">
                                <p>Login with</p>
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

                            <form class="auth-form" action="${pageContext.request.contextPath}/login" method="post">
                                <!-- CSRF Protection -->
                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                <div class="form-group">
                                    <label for="email">Email Address</label>
                                    <input type="email" id="email" name="email" placeholder="Enter your email"
                                        value="${email != null ? email : rememberedEmail}" required>
                                    <c:if test="${not empty emailError}">
                                        <span class="error">${emailError}</span>
                                    </c:if>
                                </div>

                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" id="password" name="password"
                                        placeholder="Enter your password" required>
                                    <c:if test="${not empty passwordError}">
                                        <span class="error">${passwordError}</span>
                                    </c:if>
                                </div>

                                <div class="form-options">
                                    <div class="remember-me">
                                        <input type="checkbox" id="rememberMe" name="rememberMe">
                                        <label for="rememberMe">Remember me</label>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/forgot-password"
                                        class="forgot-password">Forgot Password?</a>
                                </div>

                                <button type="submit" class="btn btn-primary btn-block">Login</button>
                            </form>

                            <div class="auth-footer">
                                <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register
                                        Now</a></p>
                            </div>
                        </div>

                        <div class="auth-image">
                            <img src="${pageContext.request.contextPath}/images/login-bg.jpg" alt="Login"
                                loading="lazy">
                        </div>
                    </div>
                </div>
            </section>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
        </body>

        </html>