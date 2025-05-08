<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Manage Properties - Admin Dashboard - Ghar Dalali</title>
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
                                    <h2>Manage Properties</h2>
                                    <a href="${pageContext.request.contextPath}/admin/properties?action=new"
                                        class="btn btn-primary">Add New Property</a>
                                </div>

                                <c:if test="${not empty successMessage}">
                                    <div class="alert alert-success">
                                        <p>${successMessage}</p>
                                    </div>
                                </c:if>
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-error">
                                        <p>${errorMessage}</p>
                                    </div>
                                </c:if>
                                <c:if test="${not empty sessionScope.successMessage}">
                                    <div class="alert alert-success">
                                        <p>${sessionScope.successMessage}</p>
                                    </div>
                                    <c:remove var="successMessage" scope="session" />
                                </c:if>

                                <div class="admin-table-container">
                                    <div class="table-actions">
                                        <div class="search-box">
                                            <input type="text" placeholder="Search properties...">
                                            <button class="btn btn-primary">Search</button>
                                        </div>
                                        <div class="filter-box">
                                            <select>
                                                <option value="">All Types</option>
                                                <option value="APARTMENT">Apartment</option>
                                                <option value="HOUSE">House</option>
                                                <option value="VILLA">Villa</option>
                                                <option value="COMMERCIAL">Commercial</option>
                                            </select>
                                        </div>
                                    </div>

                                    <table class="admin-table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Title</th>
                                                <th>Type</th>
                                                <th>Price</th>
                                                <th>Status</th>
                                                <th>Created At</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${properties}" var="property">
                                                <tr>
                                                    <td>${property.propertyId}</td>
                                                    <td>${property.propertyName}</td>
                                                    <td>${property.propertyType}</td>
                                                    <td>NPR ${property.price}</td>
                                                    <td>${property.status}</td>
                                                    <td>${property.createdAt}</td>
                                                    <td class="actions">
                                                        <a href="${pageContext.request.contextPath}/admin/properties?action=view&propertyId=${property.propertyId}"
                                                            class="btn-icon view" title="View Property">
                                                            <i class="fa-regular fa-eye"></i>
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/admin/properties?action=edit&propertyId=${property.propertyId}"
                                                            class="btn-icon edit" title="Edit Property">
                                                            <i class="fa-regular fa-pen-to-square"></i>
                                                        </a>
                                                        <form
                                                            action="${pageContext.request.contextPath}/admin/properties"
                                                            method="post" style="display: inline;">
                                                            <!-- CSRF Protection -->
                                                            <input type="hidden" name="csrfToken"
                                                                value="${sessionScope.csrfToken}">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="propertyId"
                                                                value="${property.propertyId}">
                                                            <button type="submit" class="btn-icon delete"
                                                                title="Delete Property"
                                                                onclick="return confirm('Are you sure you want to delete this property?')">
                                                                <i class="fa-regular fa-trash"></i>
                                                            </button>
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

            <script src="${pageContext.request.contextPath}/js/components-updated.js" defer></script>
            <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
        </body>

        </html>