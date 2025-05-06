<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Edit Property - Admin Dashboard - Ghar Dalali</title>
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
                                    <h2>Edit Property</h2>
                                    <a href="${pageContext.request.contextPath}/admin/properties"
                                        class="btn btn-outline">Back to Properties</a>
                                </div>

                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-error">
                                        <p>${errorMessage}</p>
                                    </div>
                                </c:if>

                                <div class="property-form-container">
                                    <form action="${pageContext.request.contextPath}/admin/properties" method="post"
                                        class="property-form">
                                        <!-- CSRF Protection -->
                                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="propertyId" value="${property.propertyId}">

                                        <div class="form-group">
                                            <label for="propertyName">Property Title <span
                                                    class="required">*</span></label>
                                            <input type="text" id="propertyName" name="propertyName"
                                                value="${property.propertyName}" required>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="propertyType">Property Type <span
                                                        class="required">*</span></label>
                                                <select id="propertyType" name="propertyType" required>
                                                    <option value="">Select Type</option>
                                                    <option value="APARTMENT" ${property.propertyType=='APARTMENT'
                                                        ? 'selected' : '' }>Apartment</option>
                                                    <option value="HOUSE" ${property.propertyType=='HOUSE' ? 'selected'
                                                        : '' }>House</option>
                                                    <option value="VILLA" ${property.propertyType=='VILLA' ? 'selected'
                                                        : '' }>Villa</option>
                                                    <option value="COMMERCIAL" ${property.propertyType=='COMMERCIAL'
                                                        ? 'selected' : '' }>Commercial</option>
                                                </select>
                                            </div>

                                            <div class="form-group">
                                                <label for="status">Status <span class="required">*</span></label>
                                                <select id="status" name="status" required>
                                                    <option value="">Select Status</option>
                                                    <option value="FOR_SALE" ${property.status=='FOR_SALE' ? 'selected'
                                                        : '' }>For Sale</option>
                                                    <option value="FOR_RENT" ${property.status=='FOR_RENT' ? 'selected'
                                                        : '' }>For Rent</option>
                                                    <option value="SOLD" ${property.status=='SOLD' ? 'selected' : '' }>
                                                        Sold</option>
                                                    <option value="RENTED" ${property.status=='RENTED' ? 'selected' : ''
                                                        }>Rented</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="description">Description <span class="required">*</span></label>
                                            <textarea id="description" name="description" rows="5"
                                                required>${property.description}</textarea>
                                        </div>

                                        <div class="form-group">
                                            <label for="location">Location <span class="required">*</span></label>
                                            <input type="text" id="location" name="location"
                                                value="${property.location}" required>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="price">Price (NPR) <span class="required">*</span></label>
                                                <input type="number" id="price" name="price" value="${property.price}"
                                                    min="0" step="0.01" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="bedrooms">Bedrooms</label>
                                                <input type="number" id="bedrooms" name="bedrooms"
                                                    value="${property.bedrooms}" min="0">
                                            </div>

                                            <div class="form-group">
                                                <label for="bathrooms">Bathrooms</label>
                                                <input type="number" id="bathrooms" name="bathrooms"
                                                    value="${property.bathrooms}" min="0">
                                            </div>
                                        </div>

                                        <div class="form-row">
                                            <div class="form-group">
                                                <label for="size">Size</label>
                                                <input type="number" id="size" name="size" value="${property.size}"
                                                    min="0" step="0.01">
                                            </div>

                                            <div class="form-group">
                                                <label for="sizeUnit">Size Unit</label>
                                                <select id="sizeUnit" name="sizeUnit">
                                                    <option value="sq.ft" ${property.sizeUnit=='sq.ft' ? 'selected' : ''
                                                        }>Square Feet</option>
                                                    <option value="sq.m" ${property.sizeUnit=='sq.m' ? 'selected' : ''
                                                        }>Square Meters</option>
                                                    <option value="acres" ${property.sizeUnit=='acres' ? 'selected' : ''
                                                        }>Acres</option>
                                                    <option value="hectares" ${property.sizeUnit=='hectares'
                                                        ? 'selected' : '' }>Hectares</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label for="imagePath">Primary Image URL</label>
                                            <input type="text" id="imagePath" name="imagePath"
                                                value="${property.primaryImagePath}">
                                            <small>Enter a URL to an image (e.g.,
                                                /images/properties/property1.jpg)</small>
                                        </div>

                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary">Update Property</button>
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