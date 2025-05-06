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
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                height="16" fill="currentColor" viewBox="0 0 16 16">
                                                                <path
                                                                    d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z" />
                                                                <path
                                                                    d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z" />
                                                            </svg>
                                                        </a>
                                                        <a href="${pageContext.request.contextPath}/admin/properties?action=edit&propertyId=${property.propertyId}"
                                                            class="btn-icon edit" title="Edit Property">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                height="16" fill="currentColor" viewBox="0 0 16 16">
                                                                <path
                                                                    d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z" />
                                                            </svg>
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
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                    height="16" fill="currentColor" viewBox="0 0 16 16">
                                                                    <path
                                                                        d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
                                                                    <path fill-rule="evenodd"
                                                                        d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z" />
                                                                </svg>
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