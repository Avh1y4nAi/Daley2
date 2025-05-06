<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Saved Properties - Ghar Dalali</title>
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
                                            <li class="active"><a
                                                    href="${pageContext.request.contextPath}/dashboard/saved-properties">Saved
                                                    Properties</a></li>
                                            <li><a href="${pageContext.request.contextPath}/dashboard/applications">My
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
                                        <h2>Saved Properties</h2>
                                        <a href="${pageContext.request.contextPath}/properties" class="btn">Browse
                                            Properties</a>
                                    </div>

                                    <div class="saved-properties-list">
                                        <c:choose>
                                            <c:when test="${empty savedProperties}">
                                                <div class="empty-state">
                                                    <img src="${pageContext.request.contextPath}/images/empty-state.svg"
                                                        alt="No saved properties">
                                                    <h3>No Saved Properties</h3>
                                                    <p>You haven't saved any properties yet. Browse our listings and
                                                        save properties you're interested in.</p>
                                                    <a href="${pageContext.request.contextPath}/properties"
                                                        class="btn">Browse Properties</a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="property-grid">
                                                    <c:forEach var="savedProperty" items="${savedProperties}">
                                                        <div class="property-card">
                                                            <div class="property-image">
                                                                <img src="${pageContext.request.contextPath}/images/properties/property1.jpg"
                                                                    alt="${savedProperty.property.propertyName}">
                                                                <span
                                                                    class="property-status ${savedProperty.property.status.toLowerCase()}">${savedProperty.property.status.replace('_',
                                                                    ' ')}</span>
                                                            </div>
                                                            <div class="property-details">
                                                                <h3><a
                                                                        href="${pageContext.request.contextPath}/property-detail?id=${savedProperty.propertyId}">${savedProperty.property.propertyName}</a>
                                                                </h3>
                                                                <p class="property-location">
                                                                    ${savedProperty.property.location}</p>
                                                                <p class="property-price">Rs.
                                                                    <fmt:formatNumber
                                                                        value="${savedProperty.property.price}"
                                                                        pattern="#,##0.00" />
                                                                </p>
                                                                <div class="property-features">
                                                                    <span><i class="fas fa-bed"></i>
                                                                        ${savedProperty.property.bedrooms} Beds</span>
                                                                    <span><i class="fas fa-bath"></i>
                                                                        ${savedProperty.property.bathrooms} Baths</span>
                                                                    <span><i class="fas fa-ruler-combined"></i>
                                                                        ${savedProperty.property.size}
                                                                        ${savedProperty.property.sizeUnit}</span>
                                                                </div>
                                                                <div class="property-actions">
                                                                    <a href="${pageContext.request.contextPath}/property-detail?id=${savedProperty.propertyId}"
                                                                        class="btn btn-sm">View Details</a>
                                                                    <form
                                                                        action="${pageContext.request.contextPath}/dashboard/saved-properties"
                                                                        method="post" class="inline-form">
                                                                        <input type="hidden" name="action"
                                                                            value="remove">
                                                                        <input type="hidden" name="propertyId"
                                                                            value="${savedProperty.propertyId}">
                                                                        <button type="submit"
                                                                            class="btn btn-sm btn-danger">Remove</button>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
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