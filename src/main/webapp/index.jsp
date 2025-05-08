<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ghar Dalali - Find Your Dream Home</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/color-palette.css?v=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar-custom.css?v=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-enhanced.css?v=1.2">
        <style>
            /* Ensure hero section styles take precedence */
            .hero .hero-content {
                text-align: left !important;
                margin: 0 !important;
                max-width: 650px !important;
            }

            .hero .search-form {
                margin: 0 !important;
            }
        </style>
    </head>

    <body>
        <jsp:include page="header.jsp" />

        <!-- Hero Section -->
        <section class="hero">
            <div class="container">
                <div class="hero-content">
                    <h2>Find Your <span class="highlight">Dream Home</span> in Nepal</h2>
                    <p>A modern real estate platform that connects buyers, sellers, and renters with the perfect
                        properties</p>
                    <div class="search-container">
                        <form class="search-form" action="${pageContext.request.contextPath}/properties" method="get">
                            <input type="text" name="search-keyword" placeholder="Search for properties...">
                            <button type="submit" class="btn btn-primary" style="border-radius: 0.7rem !important;">
                                Search
                            </button>
                        </form>
                    </div>
                    <div class="cta-buttons">
                        <a href="${pageContext.request.contextPath}/properties" class="btn btn-primary">Browse
                            Properties</a>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">Sign Up</a>
                    </div>
                </div>
            </div>
        </section>

        <!-- Featured Properties Section -->
        <section class="featured-properties">
            <div class="container">
                <h2 class="section-title">Featured Properties</h2>
                <p class="section-subtitle">Handpicked properties for you</p>
                <div class="property-carousel">
                    <!-- Property Card 1 -->
                    <div class="property-card">
                        <div class="property-image">
                            <a href="${pageContext.request.contextPath}/property-detail?id=1">
                                <img src="${pageContext.request.contextPath}/images/properties/property1.jpg"
                                    alt="Luxury Villa" loading="lazy">
                                <span class="property-status">For Sale</span>
                            </a>
                        </div>
                        <div class="property-details">
                            <h3 class="property-title">Luxury Villa</h3>
                            <p class="property-location">Kathmandu, Nepal</p>
                            <p class="property-price">NPR 25,000,000</p>
                            <div class="property-specs">
                                <span>3 Beds</span>
                                <span>2 Baths</span>
                                <span>1500 sq.ft</span>
                            </div>
                            <a href="${pageContext.request.contextPath}/property-detail?id=1"
                                class="btn btn-outline">View Details</a>
                        </div>
                    </div>

                    <!-- Property Card 2 -->
                    <div class="property-card">
                        <div class="property-image">
                            <a href="${pageContext.request.contextPath}/property-detail?id=2">
                                <img src="${pageContext.request.contextPath}/images/properties/property2.jpg"
                                    alt="Modern Apartment" loading="lazy">
                                <span class="property-status">For Rent</span>
                            </a>
                        </div>
                        <div class="property-details">
                            <h3 class="property-title">Modern Apartment</h3>
                            <p class="property-location">Pokhara, Nepal</p>
                            <p class="property-price">NPR 120,000/month</p>
                            <div class="property-specs">
                                <span>2 Beds</span>
                                <span>1 Bath</span>
                                <span>900 sq.ft</span>
                            </div>
                            <a href="${pageContext.request.contextPath}/property-detail?id=2"
                                class="btn btn-outline">View Details</a>
                        </div>
                    </div>

                    <!-- Property Card 3 -->
                    <div class="property-card">
                        <div class="property-image">
                            <a href="${pageContext.request.contextPath}/property-detail?id=3">
                                <img src="${pageContext.request.contextPath}/images/properties/property3.jpg"
                                    alt="Family Home" loading="lazy">
                                <span class="property-status">For Sale</span>
                            </a>
                        </div>
                        <div class="property-details">
                            <h3 class="property-title">Family Home</h3>
                            <p class="property-location">Lalitpur, Nepal</p>
                            <p class="property-price">NPR 18,000,000</p>
                            <div class="property-specs">
                                <span>4 Beds</span>
                                <span>3 Baths</span>
                                <span>2000 sq.ft</span>
                            </div>
                            <a href="${pageContext.request.contextPath}/property-detail?id=3"
                                class="btn btn-outline">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Why Choose Us Section -->
        <section class="why-choose-us">
            <div class="container">
                <h2 class="section-title">Why Choose Us</h2>
                <p class="section-subtitle">We make it easy for you to find your dream home</p>
                <div class="features-grid">
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/images/icons/home-icon.svg"
                                alt="Premium Properties">
                        </div>
                        <h3>Premium Properties</h3>
                        <p>Exclusive high-quality properties you won't find anywhere else</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/images/icons/price-icon.svg"
                                alt="Competitive Pricing">
                        </div>
                        <h3>Competitive Pricing</h3>
                        <p>Discover the best deals with our market-based pricing</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/images/icons/agent-icon.svg"
                                alt="Professional Agents">
                        </div>
                        <h3>Professional Agents</h3>
                        <p>Our agents provide personalized guidance through every step</p>
                    </div>
                    <div class="feature">
                        <div class="feature-icon">
                            <img src="${pageContext.request.contextPath}/images/icons/secure-icon.svg"
                                alt="Secure Platform">
                        </div>
                        <h3>Secure Platform</h3>
                        <p>Advanced security measures to protect your data and transactions</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- Call to Action Section -->
        <section class="cta-section">
            <div class="container">
                <h2>Schedule Your Property Tour Today</h2>
                <a href="${pageContext.request.contextPath}/contact" class="btn btn-primary cta-btn">Contact Us Now</a>
            </div>
        </section>

        <jsp:include page="footer.jsp" />

        <script src="${pageContext.request.contextPath}/js/components-updated.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
    </body>

    </html>