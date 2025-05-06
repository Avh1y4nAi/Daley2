<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Properties - Ghar Dalali</title>
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/properties.css">
            </head>

            <body>
                <jsp:include page="header.jsp" />

                <!-- Page Header -->
                <section class="page-header">
                    <div class="container">
                        <h1>Browse <span class="highlight">Properties</span></h1>
                        <p>Find your dream home from our extensive collection of premium properties across Nepal</p>
                        <div class="search-container">
                            <form class="search-form" action="${pageContext.request.contextPath}/properties"
                                method="get">
                                <input type="text" name="search-keyword"
                                    placeholder="Search by location, property type, or keyword..." value="${keyword}">
                                <button type="submit" class="btn btn-primary">Search</button>
                            </form>
                        </div>
                    </div>
                </section>

                <!-- Properties Section -->
                <section class="properties-section">
                    <div class="container">
                        <div class="properties-container">
                            <!-- Filter Sidebar -->
                            <div class="filter-sidebar">
                                <h3>Filter Properties</h3>
                                <form class="filter-form" action="${pageContext.request.contextPath}/properties"
                                    method="get">
                                    <div class="filter-group search-filter">
                                        <label for="search-keyword">Search</label>
                                        <input type="text" id="search-keyword" name="search-keyword"
                                            placeholder="Enter keywords..." value="${keyword}">
                                    </div>
                                    <div class="filter-group">
                                        <label for="property-type">Property Type</label>
                                        <select id="property-type" name="property-type">
                                            <option value="" ${empty propertyType ? 'selected' : '' }>All Types</option>
                                            <option value="APARTMENT" ${propertyType=='APARTMENT' ? 'selected' : '' }>
                                                Apartment</option>
                                            <option value="HOUSE" ${propertyType=='HOUSE' ? 'selected' : '' }>House
                                            </option>
                                            <option value="VILLA" ${propertyType=='VILLA' ? 'selected' : '' }>Villa
                                            </option>
                                            <option value="COMMERCIAL" ${propertyType=='COMMERCIAL' ? 'selected' : '' }>
                                                Commercial</option>
                                            <option value="LAND" ${propertyType=='LAND' ? 'selected' : '' }>Land
                                            </option>
                                        </select>
                                    </div>

                                    <div class="filter-group">
                                        <label for="price-range">Price Range</label>
                                        <div class="price-inputs">
                                            <input type="number" id="min-price" name="min-price" placeholder="Min"
                                                value="${minPrice}">
                                            <span>to</span>
                                            <input type="number" id="max-price" name="max-price" placeholder="Max"
                                                value="${maxPrice}">
                                        </div>
                                    </div>

                                    <div class="filter-group">
                                        <label for="bedrooms">Bedrooms</label>
                                        <select id="bedrooms" name="bedrooms">
                                            <option value="" ${empty bedrooms ? 'selected' : '' }>Any</option>
                                            <option value="1" ${bedrooms==1 ? 'selected' : '' }>1+</option>
                                            <option value="2" ${bedrooms==2 ? 'selected' : '' }>2+</option>
                                            <option value="3" ${bedrooms==3 ? 'selected' : '' }>3+</option>
                                            <option value="4" ${bedrooms==4 ? 'selected' : '' }>4+</option>
                                            <option value="5" ${bedrooms==5 ? 'selected' : '' }>5+</option>
                                        </select>
                                    </div>

                                    <div class="filter-group">
                                        <label for="bathrooms">Bathrooms</label>
                                        <select id="bathrooms" name="bathrooms">
                                            <option value="" ${empty bathrooms ? 'selected' : '' }>Any</option>
                                            <option value="1" ${bathrooms==1 ? 'selected' : '' }>1+</option>
                                            <option value="2" ${bathrooms==2 ? 'selected' : '' }>2+</option>
                                            <option value="3" ${bathrooms==3 ? 'selected' : '' }>3+</option>
                                            <option value="4" ${bathrooms==4 ? 'selected' : '' }>4+</option>
                                        </select>
                                    </div>

                                    <div class="filter-group">
                                        <label for="property-status">Status</label>
                                        <select id="property-status" name="property-status">
                                            <option value="" ${empty status ? 'selected' : '' }>All</option>
                                            <option value="FOR_SALE" ${status=='FOR_SALE' ? 'selected' : '' }>For Sale
                                            </option>
                                            <option value="FOR_RENT" ${status=='FOR_RENT' ? 'selected' : '' }>For Rent
                                            </option>
                                        </select>
                                    </div>

                                    <div class="filter-buttons">
                                        <button type="submit" class="btn btn-primary">Apply Filters</button>
                                        <button type="reset" class="btn btn-outline">Reset</button>
                                    </div>
                                </form>
                            </div>

                            <!-- Properties List -->
                            <div class="properties-list">
                                <div class="properties-header">
                                    <div class="properties-count">
                                        <p>Showing <span>${propertiesCount}</span> properties</p>
                                    </div>
                                    <div class="properties-sort">
                                        <label for="sort-by">Sort by:</label>
                                        <select id="sort-by" name="sort-by" onchange="this.form.submit()">
                                            <option value="newest" ${sortBy=='newest' || empty sortBy ? 'selected' : ''
                                                }>Newest</option>
                                            <option value="price-asc" ${sortBy=='price-asc' ? 'selected' : '' }>Price
                                                (Low to High)</option>
                                            <option value="price-desc" ${sortBy=='price-desc' ? 'selected' : '' }>Price
                                                (High to Low)</option>
                                        </select>
                                    </div>
                                    <div class="view-toggle">
                                        <button class="grid-view active">Grid</button>
                                        <button class="list-view">List</button>
                                    </div>
                                </div>

                                <div class="properties-grid">
                                    <c:forEach var="property" items="${properties}">
                                        <!-- Property Card -->
                                        <div class="property-card">
                                            <div class="property-image">
                                                <a
                                                    href="${pageContext.request.contextPath}/property-detail?id=${property.propertyId}">
                                                    <img src="${pageContext.request.contextPath}/${property.primaryImagePath}"
                                                        alt="${property.propertyName}" loading="lazy">
                                                    <span class="property-status">${property.status == 'FOR_SALE' ? 'For
                                                        Sale' : property.status == 'FOR_RENT' ? 'For Rent' :
                                                        property.status}</span>
                                                </a>
                                            </div>
                                            <div class="property-details">
                                                <h3 class="property-title">${property.propertyName}</h3>
                                                <p class="property-location">${property.location}</p>
                                                <p class="property-price">
                                                    <fmt:formatNumber value="${property.price}" type="currency"
                                                        currencySymbol="NPR " />
                                                    ${property.status == 'FOR_RENT' ? '/month' : ''}
                                                </p>
                                                <div class="property-specs">
                                                    <c:if test="${not empty property.bedrooms}">
                                                        <span>${property.bedrooms} Beds</span>
                                                    </c:if>
                                                    <c:if test="${not empty property.bathrooms}">
                                                        <span>${property.bathrooms} Baths</span>
                                                    </c:if>
                                                    <c:if test="${not empty property.size}">
                                                        <span>${property.size} ${property.sizeUnit}</span>
                                                    </c:if>
                                                </div>
                                                <a href="${pageContext.request.contextPath}/property-detail?id=${property.propertyId}"
                                                    class="btn btn-outline">View Details</a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <!-- Pagination -->
                                <div class="pagination">
                                    <a href="#" class="page-link active">1</a>
                                    <a href="#" class="page-link">2</a>
                                    <a href="#" class="page-link">3</a>
                                    <a href="#" class="page-link next">Next &raquo;</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <jsp:include page="footer.jsp" />

                <script src="${pageContext.request.contextPath}/js/components-updated.js" defer></script>
                <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
            </body>

            </html>