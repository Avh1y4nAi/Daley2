<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Add New Property - Admin Dashboard - Ghar Dalali</title>
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
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/property-forms.css">
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
                                    <h2>Add New Property</h2>
                                    <a href="${pageContext.request.contextPath}/admin/properties"
                                        class="btn btn-secondary">Back to Properties</a>
                                </div>

                                <div class="form-container">
                                    <c:if test="${not empty errorMessage}">
                                        <div class="alert alert-error">
                                            <p>${errorMessage}</p>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty successMessage}">
                                        <div class="alert alert-success">
                                            <p>${successMessage}</p>
                                        </div>
                                    </c:if>

                                    <form action="${pageContext.request.contextPath}/admin/properties" method="post"
                                        class="property-form">
                                        <!-- CSRF Protection -->
                                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                        <input type="hidden" name="action" value="add">

                                        <div class="form-group">
                                            <label for="propertyName">Property Title*</label>
                                            <input type="text" id="propertyName" name="propertyName" required>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="propertyType">Property Type*</label>
                                                <select id="propertyType" name="propertyType" required>
                                                    <option value="">Select Type</option>
                                                    <option value="APARTMENT">Apartment</option>
                                                    <option value="HOUSE">House</option>
                                                    <option value="VILLA">Villa</option>
                                                    <option value="COMMERCIAL">Commercial</option>
                                                    <option value="LAND">Land</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label for="status">Status*</label>
                                                <select id="status" name="status" required>
                                                    <option value="">Select Status</option>
                                                    <option value="FOR_SALE">For Sale</option>
                                                    <option value="FOR_RENT">For Rent</option>
                                                    <option value="SOLD">Sold</option>
                                                    <option value="RENTED">Rented</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="location">Location*</label>
                                            <input type="text" id="location" name="location" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="description">Description*</label>
                                            <textarea id="description" name="description" rows="5" required></textarea>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="price">Price (NPR)*</label>
                                                <input type="number" id="price" name="price" min="0" step="0.01"
                                                    required>
                                            </div>

                                            <div class="form-group">
                                                <label for="size">Size</label>
                                                <input type="number" id="size" name="size" min="0" step="0.01">
                                            </div>

                                            <div class="form-group">
                                                <label for="sizeUnit">Unit</label>
                                                <select id="sizeUnit" name="sizeUnit">
                                                    <option value="sq.ft">sq.ft</option>
                                                    <option value="sq.m">sq.m</option>
                                                    <option value="acres">acres</option>
                                                    <option value="hectares">hectares</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="bedrooms">Bedrooms</label>
                                                <input type="number" id="bedrooms" name="bedrooms" min="0">
                                            </div>

                                            <div class="form-group">
                                                <label for="bathrooms">Bathrooms</label>
                                                <input type="number" id="bathrooms" name="bathrooms" min="0">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="imagePath">Primary Image URL</label>
                                            <input type="text" id="imagePath" name="imagePath"
                                                placeholder="https://example.com/image.jpg">
                                            <small>Enter a URL for the property image. You can add more images
                                                later.</small>
                                        </div>

                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary">Add Property</button>
                                            <a href="${pageContext.request.contextPath}/admin/properties"
                                                class="btn btn-secondary">Cancel</a>
                                        </div>
                                    </form>
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