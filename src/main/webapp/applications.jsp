<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>My Applications - Ghar Dalali</title>
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
                                            <img src="${pageContext.request.contextPath}/images/users/default-avatar.png"
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
                                            <li class="active"><a
                                                    href="${pageContext.request.contextPath}/dashboard/applications">My
                                                    Applications</a></li>
                                            <li><a href="${pageContext.request.contextPath}/dashboard/reviews">My
                                                    Reviews</a></li>
                                            <li><a href="${pageContext.request.contextPath}/dashboard/payment-history">Payment
                                                    History</a></li>
                                            <li><a href="${pageContext.request.contextPath}/dashboard/change-password">Change
                                                    Password</a></li>
                                        </ul>
                                    </nav>
                                </div>
                                <div class="dashboard-content">
                                    <div class="dashboard-header">
                                        <h2>My Applications</h2>
                                        <a href="${pageContext.request.contextPath}/properties" class="btn">Browse
                                            Properties</a>
                                    </div>

                                    <div class="applications-list">
                                        <c:choose>
                                            <c:when test="${empty applications}">
                                                <div class="empty-state">
                                                    <img src="${pageContext.request.contextPath}/images/empty-state.svg"
                                                        alt="No applications">
                                                    <h3>No Applications</h3>
                                                    <p>You haven't applied for any properties yet. Browse our listings
                                                        and apply for properties you're interested in.</p>
                                                    <a href="${pageContext.request.contextPath}/properties"
                                                        class="btn">Browse Properties</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="applications-table">
                                                    <table>
                                                        <thead>
                                                            <tr>
                                                                <th>Property</th>
                                                                <th>Location</th>
                                                                <th>Price</th>
                                                                <th>Applied Date</th>
                                                                <th>Status</th>
                                                                <th>Actions</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="application" items="${applications}">
                                                                <tr>
                                                                    <td>
                                                                        <div class="property-info">
                                                                            <img src="${pageContext.request.contextPath}/images/properties/property1.jpg"
                                                                                alt="${application.property.propertyName}">
                                                                            <div>
                                                                                <h4>${application.property.propertyName}
                                                                                </h4>
                                                                                <p>${application.property.propertyType.replace('_',
                                                                                    ' ')}</p>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td>${application.property.location}</td>
                                                                    <td>Rs.
                                                                        <fmt:formatNumber
                                                                            value="${application.property.price}"
                                                                            pattern="#,##0.00" />
                                                                    </td>
                                                                    <td>
                                                                        <fmt:formatDate value="${application.appliedAt}"
                                                                            pattern="MMM dd, yyyy" />
                                                                    </td>
                                                                    <td>
                                                                        <span
                                                                            class="status-badge ${application.status.toLowerCase()}">${application.status}</span>
                                                                    </td>
                                                                    <td>
                                                                        <div class="action-buttons">
                                                                            <a href="${pageContext.request.contextPath}/property-detail?id=${application.propertyId}"
                                                                                class="btn btn-sm">View Property</a>
                                                                            <c:if
                                                                                test="${application.status eq 'PENDING'}">
                                                                                <form
                                                                                    action="${pageContext.request.contextPath}/dashboard/applications"
                                                                                    method="post" class="inline-form">
                                                                                    <input type="hidden" name="action"
                                                                                        value="cancel">
                                                                                    <input type="hidden"
                                                                                        name="applicationId"
                                                                                        value="${application.applicationId}">
                                                                                    <button type="submit"
                                                                                        class="btn btn-sm btn-danger">Cancel</button>
                                                                                </form>
                                                                            </c:if>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
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