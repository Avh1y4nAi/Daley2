<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Manage Applications - Admin Dashboard - Ghar Dalali</title>
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
        </head>

        <body>
            <jsp:include page="header.jsp" />

            <main>
                <section class="dashboard-section">
                    <div class="container">
                        <h1 class="dashboard-title">Admin Dashboard</h1>

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
                                        <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/admin/users">Manage Users</a>
                                        </li>
                                        <li><a href="${pageContext.request.contextPath}/admin/properties">Manage
                                                Properties</a></li>
                                        <li class="active"><a
                                                href="${pageContext.request.contextPath}/admin/applications">Manage
                                                Applications</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/reviews">Manage
                                                Reviews</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/payments">Payment
                                                History</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/change-password">Change
                                                Password</a></li>
                                    </ul>
                                </nav>
                            </div>
                            <div class="dashboard-content">
                                <div class="dashboard-header">
                                    <h2>Manage Applications</h2>
                                </div>

                                <div class="admin-table-container">
                                    <div class="table-actions">
                                        <div class="search-box">
                                            <input type="text" placeholder="Search applications...">
                                            <button class="btn btn-primary">Search</button>
                                        </div>
                                        <div class="filter-box">
                                            <select>
                                                <option value="">All Statuses</option>
                                                <option value="PENDING">Pending</option>
                                                <option value="APPROVED">Approved</option>
                                                <option value="REJECTED">Rejected</option>
                                            </select>
                                        </div>
                                    </div>

                                    <table class="admin-table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>User</th>
                                                <th>Property</th>
                                                <th>Status</th>
                                                <th>Applied At</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${applications}" var="application">
                                                <tr>
                                                    <td>${application.applicationId}</td>
                                                    <td>${application.userName}</td>
                                                    <td>${application.propertyTitle}</td>
                                                    <td>${application.status}</td>
                                                    <td>${application.appliedAt}</td>
                                                    <td class="actions">
                                                        <button class="btn-icon view"
                                                            title="View Application">V</button>
                                                        <form
                                                            action="${pageContext.request.contextPath}/admin/applications"
                                                            method="post" style="display: inline;">
                                                            <input type="hidden" name="action" value="approve">
                                                            <input type="hidden" name="applicationId"
                                                                value="${application.applicationId}">
                                                            <button type="submit" class="btn-icon approve"
                                                                title="Approve Application">A</button>
                                                        </form>
                                                        <form
                                                            action="${pageContext.request.contextPath}/admin/applications"
                                                            method="post" style="display: inline;">
                                                            <input type="hidden" name="action" value="reject">
                                                            <input type="hidden" name="applicationId"
                                                                value="${application.applicationId}">
                                                            <button type="submit" class="btn-icon reject"
                                                                title="Reject Application">R</button>
                                                        </form>
                                                        <form
                                                            action="${pageContext.request.contextPath}/admin/applications"
                                                            method="post" style="display: inline;">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="applicationId"
                                                                value="${application.applicationId}">
                                                            <button type="submit" class="btn-icon delete"
                                                                title="Delete Application"
                                                                onclick="return confirm('Are you sure you want to delete this application?')">D</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
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