<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Forgot Password - Ghar Dalali</title>
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

            <!-- Forgot Password Section -->
            <section class="auth-section">
                <div class="container">
                    <div class="auth-container">
                        <div class="auth-content">
                            <h2>Forgot Password</h2>
                            <p>Enter your email address and we'll send you a link to reset your password.</p>

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

                            <form class="auth-form" action="${pageContext.request.contextPath}/forgot-password"
                                method="post">
                                <!-- CSRF Protection -->
                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                <div class="form-group">
                                    <label for="email">Email Address</label>
                                    <input type="email" id="email" name="email" placeholder="Enter your email" required>
                                </div>

                                <button type="submit" class="btn btn-primary btn-block">Send Reset Link</button>
                            </form>

                            <div class="auth-footer">
                                <p>Remember your password? <a href="${pageContext.request.contextPath}/login">Back to
                                        Login</a></p>
                            </div>
                        </div>

                        <div class="auth-image">
                            <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg"
                                alt="Forgot Password" loading="lazy">
                        </div>
                    </div>
                </div>
            </section>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
        </body>

        </html>