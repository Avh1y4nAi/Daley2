<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>${property.propertyName} - Property Details | Ghar Dalali</title>
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/css/property-detail.css">
            </head>

            <body>
                <jsp:include page="header.jsp" />

                <!-- Property Detail Section -->
                <section class="property-detail">
                    <div class="container">
                        <div class="property-header">
                            <div class="property-title-section">
                                <h1>${property.propertyName}</h1>
                                <p class="property-location">${property.location}</p>
                                <div class="property-status-price">
                                    <span class="property-status">
                                        ${property.status == 'FOR_SALE' ? 'For Sale' : property.status == 'FOR_RENT' ?
                                        'For Rent' : property.status}
                                    </span>
                                    <span class="property-price">
                                        <fmt:formatNumber value="${property.price}" type="currency"
                                            currencySymbol="NPR " />
                                        ${property.status == 'FOR_RENT' ? '/month' : ''}
                                    </span>
                                </div>
                            </div>
                            <div class="property-actions">
                                <button class="btn btn-outline">Save Property</button>
                                <button class="btn btn-primary">Schedule Viewing</button>
                            </div>
                        </div>

                        <!-- Property Gallery -->
                        <div class="property-gallery">
                            <div class="main-image">
                                <img src="${pageContext.request.contextPath}/${property.primaryImagePath}"
                                    alt="${property.propertyName}" loading="lazy">
                            </div>
                            <div class="thumbnail-images">
                                <c:forEach var="imagePath" items="${property.imagePaths}" varStatus="status">
                                    <img src="${pageContext.request.contextPath}/${imagePath}"
                                        alt="${property.propertyName}"
                                        class="${imagePath == property.primaryImagePath ? 'active' : ''}"
                                        loading="lazy">
                                </c:forEach>
                            </div>
                        </div>

                        <div class="property-content">
                            <div class="property-main-content">
                                <!-- Property Overview -->
                                <section class="property-section">
                                    <h2 class="section-title">Overview</h2>
                                    <div class="property-overview">
                                        <div class="overview-item">
                                            <span class="item-label">Property Type</span>
                                            <span class="item-value">${property.propertyType}</span>
                                        </div>
                                        <c:if test="${not empty property.bedrooms}">
                                            <div class="overview-item">
                                                <span class="item-label">Bedrooms</span>
                                                <span class="item-value">${property.bedrooms}</span>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty property.bathrooms}">
                                            <div class="overview-item">
                                                <span class="item-label">Bathrooms</span>
                                                <span class="item-value">${property.bathrooms}</span>
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty property.size}">
                                            <div class="overview-item">
                                                <span class="item-label">Size</span>
                                                <span class="item-value">${property.size} ${property.sizeUnit}</span>
                                            </div>
                                        </c:if>
                                        <div class="overview-item">
                                            <span class="item-label">Year Built</span>
                                            <span class="item-value">${property.yearBuilt}</span>
                                        </div>
                                        <div class="overview-item">
                                            <span class="item-label">Garage</span>
                                            <span class="item-value">${property.garage}</span>
                                        </div>
                                    </div>
                                </section>

                                <!-- Property Description -->
                                <section class="property-section">
                                    <h2 class="section-title">Description</h2>
                                    <div class="property-description">
                                        <p>${property.description}</p>
                                    </div>
                                </section>

                                <!-- Property Features -->
                                <section class="property-section">
                                    <h2 class="section-title">Features & Amenities</h2>
                                    <div class="property-features">
                                        <ul class="features-list">
                                            <c:forEach var="feature" items="${property.features}">
                                                <li>${feature}</li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </section>

                                <!-- Property Location -->
                                <section class="property-section">
                                    <h2 class="section-title">Location</h2>
                                    <div class="property-location-map">
                                        <div class="map-placeholder">
                                            <iframe
                                                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3532.2747151202476!2d85.31404491506156!3d27.70496798279492!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x39eb19a6a8aaad7f%3A0x64f5d91e7f618b1!2sDurbar%20Marg%2C%20Kathmandu%2044600!5e0!3m2!1sen!2snp!4v1623825788449!5m2!1sen!2snp"
                                                width="100%" height="300" style="border:0;" allowfullscreen=""
                                                loading="lazy"></iframe>
                                        </div>
                                        <div class="location-details">
                                            <p><strong>Address:</strong> ${property.address}</p>
                                            <p><strong>Neighborhood:</strong> ${property.neighborhood}</p>
                                            <div class="nearby-places">
                                                <h3>Nearby Places</h3>
                                                <ul>
                                                    <c:forEach var="nearbyPlace" items="${property.nearbyPlaces}">
                                                        <li>${nearbyPlace}</li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </section>

                                <!-- Property Reviews -->
                                <section class="property-section">
                                    <h2 class="section-title">Reviews</h2>
                                    <div class="property-reviews">
                                        <div class="review-summary">
                                            <div class="average-rating">
                                                <span class="rating-value">4.5</span>
                                                <div class="rating-stars">
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star half-filled">★</span>
                                                </div>
                                                <span class="review-count">Based on 12 reviews</span>
                                            </div>
                                        </div>

                                        <div class="review-list">
                                            <!-- Review 1 -->
                                            <div class="review-item">
                                                <div class="reviewer-info">
                                                    <div class="reviewer-avatar">
                                                        <img src="${pageContext.request.contextPath}/images/users/user1.jpg"
                                                            alt="John Doe">
                                                    </div>
                                                    <div class="reviewer-details">
                                                        <div class="reviewer-name">John Doe</div>
                                                        <div class="review-date">June 15, 2023</div>
                                                    </div>
                                                </div>
                                                <div class="review-rating">
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                </div>
                                                <div class="review-content">
                                                    <p>Amazing property with stunning views! The villa is spacious,
                                                        well-maintained,
                                                        and has all the amenities you could ask for. The location is
                                                        perfect - quiet
                                                        but still close to everything.</p>
                                                </div>
                                            </div>

                                            <!-- Review 2 -->
                                            <div class="review-item">
                                                <div class="reviewer-info">
                                                    <div class="reviewer-avatar">
                                                        <img src="${pageContext.request.contextPath}/images/users/user2.jpg"
                                                            alt="Jane Smith">
                                                    </div>
                                                    <div class="reviewer-details">
                                                        <div class="reviewer-name">Jane Smith</div>
                                                        <div class="review-date">May 22, 2023</div>
                                                    </div>
                                                </div>
                                                <div class="review-rating">
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star filled">★</span>
                                                    <span class="star">★</span>
                                                </div>
                                                <div class="review-content">
                                                    <p>Great property overall. The interior is beautiful and the garden
                                                        is
                                                        well-maintained. The only downside is that some of the
                                                        appliances are a bit
                                                        outdated.</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="write-review">
                                            <h3>Write a Review</h3>
                                            <form class="review-form"
                                                action="${pageContext.request.contextPath}/property-review"
                                                method="post">
                                                <input type="hidden" name="propertyId" value="${property.propertyId}">
                                                <div class="form-group">
                                                    <label for="rating">Rating</label>
                                                    <div class="rating-input">
                                                        <span class="star">★</span>
                                                        <span class="star">★</span>
                                                        <span class="star">★</span>
                                                        <span class="star">★</span>
                                                        <span class="star">★</span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="review-text">Your Review</label>
                                                    <textarea id="review-text" name="reviewText" rows="5"
                                                        placeholder="Share your experience with this property"></textarea>
                                                </div>
                                                <button type="submit" class="btn btn-primary">Submit Review</button>
                                            </form>
                                        </div>
                                    </div>
                                </section>
                            </div>

                            <div class="property-sidebar">
                                <!-- Contact Agent Form -->
                                <div class="sidebar-section contact-agent">
                                    <h3>Contact Agent</h3>
                                    <div class="agent-info">
                                        <div class="agent-avatar">
                                            <img src="${pageContext.request.contextPath}/images/users/user1.jpg"
                                                alt="Ram Sharma">
                                        </div>
                                        <div class="agent-details">
                                            <div class="agent-name">Ram Sharma</div>
                                            <div class="agent-contact">
                                                <p>
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14"
                                                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <path
                                                            d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z">
                                                        </path>
                                                    </svg>
                                                    +977 9876543210
                                                </p>
                                                <p>
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14"
                                                        viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                        stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <path
                                                            d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z">
                                                        </path>
                                                        <polyline points="22,6 12,13 2,6"></polyline>
                                                    </svg>
                                                    ram@ghardalali.com
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <form class="contact-form" action="${pageContext.request.contextPath}/contact-agent"
                                        method="post">
                                        <input type="hidden" name="propertyId" value="${property.propertyId}">
                                        <div class="form-group">
                                            <label for="name">Your Name</label>
                                            <input type="text" id="name" name="name" placeholder="Enter your name"
                                                required>
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Your Email</label>
                                            <input type="email" id="email" name="email" placeholder="Enter your email"
                                                required>
                                        </div>
                                        <div class="form-group">
                                            <label for="phone">Your Phone</label>
                                            <input type="tel" id="phone" name="phone"
                                                placeholder="Enter your phone number">
                                        </div>
                                        <div class="form-group">
                                            <label for="message">Message</label>
                                            <textarea id="message" name="message" rows="4"
                                                placeholder="I'm interested in this property" required></textarea>
                                        </div>
                                        <button type="submit" class="btn btn-primary btn-block">Send Message</button>
                                    </form>
                                </div>

                                <!-- Apply for Property -->
                                <div class="sidebar-section apply-property">
                                    <h3>Apply for This Property</h3>
                                    <p>Ready to make this property yours? Apply now to start the process.</p>
                                    <a href="${pageContext.request.contextPath}/apply?id=${property.propertyId}"
                                        class="btn btn-secondary btn-block">Apply Now</a>
                                </div>

                                <!-- Similar Properties -->
                                <div class="sidebar-section similar-properties">
                                    <h3>Similar Properties</h3>
                                    <div class="similar-property-list">
                                        <c:forEach var="similarProperty" items="${similarProperties}">
                                            <!-- Similar Property -->
                                            <div class="similar-property-item">
                                                <div class="similar-property-image">
                                                    <a
                                                        href="${pageContext.request.contextPath}/property-detail?id=${similarProperty.propertyId}">
                                                        <img src="${pageContext.request.contextPath}/${similarProperty.primaryImagePath}"
                                                            alt="${similarProperty.propertyName}" loading="lazy">
                                                    </a>
                                                </div>
                                                <div class="similar-property-details">
                                                    <h4>${similarProperty.propertyName}</h4>
                                                    <p class="similar-property-price">
                                                        <fmt:formatNumber value="${similarProperty.price}"
                                                            type="currency" currencySymbol="NPR " />
                                                    </p>
                                                    <a
                                                        href="${pageContext.request.contextPath}/property-detail?id=${similarProperty.propertyId}">View
                                                        Details</a>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <jsp:include page="footer.jsp" />

                <script src="${pageContext.request.contextPath}/js/components-updated.js" defer></script>
                <script src="${pageContext.request.contextPath}/js/property-detail.js" defer></script>
            </body>

            </html>