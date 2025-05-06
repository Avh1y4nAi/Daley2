<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Payment History - Ghar Dalali</title>
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
                                        <li><a href="${pageContext.request.contextPath}/dashboard/profile">My
                                                Profile</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/saved-properties">Saved
                                                Properties</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/applications">My
                                                Applications</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/reviews">My
                                                Reviews</a></li>
                                        <li class="active"><a
                                                href="${pageContext.request.contextPath}/dashboard/payment-history">Payment
                                                History</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/change-password">Change
                                                Password</a></li>
                                    </ul>
                                </nav>
                            </div>
                            <div class="dashboard-content">
                                <div class="dashboard-header">
                                    <h2>Payment History</h2>
                                </div>

                                <c:if test="${empty payments}">
                                    <div class="empty-state">
                                        <p>You don't have any payment records yet.</p>
                                        <p>Your payment history will appear here once you make payments for properties
                                            or services.</p>
                                    </div>
                                </c:if>

                                <c:if test="${not empty payments}">
                                    <div class="table-responsive">
                                        <table class="data-table">
                                            <thead>
                                                <tr>
                                                    <th>Payment ID</th>
                                                    <th>Date</th>
                                                    <th>Description</th>
                                                    <th>Amount</th>
                                                    <th>Status</th>
                                                    <th>Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${payments}" var="payment">
                                                    <tr>
                                                        <td>${payment.paymentId}</td>
                                                        <td>${payment.paymentDate}</td>
                                                        <td>${payment.description}</td>
                                                        <td>Rs. ${payment.amount}</td>
                                                        <td>
                                                            <span
                                                                class="status-badge ${payment.status.toLowerCase()}">${payment.status}</span>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-sm">View Receipt</button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
        </body>

        </html>