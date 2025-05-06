<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Ghar Dalali</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <!-- Page Header -->
    <section class="page-header">
        <div class="container">
            <h1>About Us</h1>
            <p>Learn more about Ghar Dalali and our mission</p>
        </div>
    </section>

    <!-- About Section -->
    <section class="about-section">
        <div class="container">
            <div class="about-content">
                <div class="about-image">
                    <img src="${pageContext.request.contextPath}/images/about-bg.jpg" alt="About Ghar Dalali">
                </div>
                <div class="about-text">
                    <h2>Our Story</h2>
                    <p>Ghar Dalali was founded in 2020 with a simple mission: to make property buying, selling, and
                        renting easier and more transparent for everyone in Nepal.</p>
                    <p>What started as a small team of real estate enthusiasts has grown into a trusted platform
                        connecting thousands of property seekers with their dream homes and helping property owners find
                        the right buyers and tenants.</p>
                    <p>Our platform leverages technology to streamline the property transaction process, providing a
                        user-friendly interface for browsing properties, connecting with agents, and completing
                        transactions securely.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Mission & Vision Section -->
    <section class="mission-vision-section">
        <div class="container">
            <div class="mission-vision-content">
                <div class="mission-box">
                    <h2>Our Mission</h2>
                    <p>To revolutionize the real estate industry in Nepal by providing a transparent, efficient, and
                        user-friendly platform that connects property seekers with their ideal properties and empowers
                        property owners to maximize their investments.</p>
                </div>
                <div class="vision-box">
                    <h2>Our Vision</h2>
                    <p>To become the most trusted and comprehensive real estate platform in Nepal, known for our
                        integrity, innovation, and commitment to customer satisfaction.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Team Section -->
    <section class="team-section">
        <div class="container">
            <h2 class="section-title">Meet Our Team</h2>
            <div class="team-grid">
                <!-- Team Member 1 -->
                <div class="team-member">
                    <div class="member-image">
                        <img src="${pageContext.request.contextPath}/images/team/team1.jpg" alt="Aviyan Khanal">
                    </div>
                    <div class="member-info">
                        <h3>Aviyan Khanal</h3>
                        <p class="member-role">Founder & CEO</p>
                        <p class="member-bio">With over 10 years of experience in real estate, Aviyan founded Ghar
                            Dalali to address the challenges he observed in the traditional property market.</p>
                        <div class="member-social">
                            <a href="#" class="social-link">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path>
                                </svg>
                            </a>
                            <a href="#" class="social-link">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path
                                        d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z">
                                    </path>
                                    <rect x="2" y="9" width="4" height="12"></rect>
                                    <circle cx="4" cy="4" r="2"></circle>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Team Member 2 -->
                <div class="team-member">
                    <div class="member-image">
                        <img src="${pageContext.request.contextPath}/images/team/team2.jpg" alt="Paurakh Pyakurel">
                    </div>
                    <div class="member-info">
                        <h3>Paurakh Pyakurel</h3>
                        <p class="member-role">Chief Operations Officer</p>
                        <p class="member-bio">Paurakh oversees the day-to-day operations of Ghar Dalali, ensuring that
                            our platform runs smoothly and our customers receive exceptional service.</p>
                        <div class="member-social">
                            <a href="#" class="social-link">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path>
                                </svg>
                            </a>
                            <a href="#" class="social-link">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                    fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                    stroke-linejoin="round">
                                    <path
                                        d="M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z">
                                    </path>
                                    <rect x="2" y="9" width="4" height="12"></rect>
                                    <circle cx="4" cy="4" r="2"></circle>
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Values Section -->
    <section class="values-section">
        <div class="container">
            <h2 class="section-title">Our Values</h2>
            <div class="values-grid">
                <div class="value-item">
                    <div class="value-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path
                                d="M20.42 4.58a5.4 5.4 0 0 0-7.65 0l-.77.78-.77-.78a5.4 5.4 0 0 0-7.65 0C1.46 6.7 1.33 10.28 4 13l8 8 8-8c2.67-2.72 2.54-6.3.42-8.42z">
                            </path>
                        </svg>
                    </div>
                    <h3>Integrity</h3>
                    <p>We operate with honesty and transparency in all our dealings, building trust with our customers
                        and partners.</p>
                </div>

                <div class="value-item">
                    <div class="value-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="10"></circle>
                            <line x1="12" y1="8" x2="12" y2="12"></line>
                            <line x1="12" y1="16" x2="12.01" y2="16"></line>
                        </svg>
                    </div>
                    <h3>Innovation</h3>
                    <p>We continuously seek new ways to improve our platform and services, embracing technology to solve
                        real estate challenges.</p>
                </div>

                <div class="value-item">
                    <div class="value-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                            <circle cx="9" cy="7" r="4"></circle>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                        </svg>
                    </div>
                    <h3>Customer Focus</h3>
                    <p>We put our customers at the center of everything we do, striving to exceed their expectations at
                        every touchpoint.</p>
                </div>

                <div class="value-item">
                    <div class="value-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 24 24" fill="none"
                            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                        </svg>
                    </div>
                    <h3>Community</h3>
                    <p>We are committed to giving back to the communities we serve and promoting sustainable real estate
                        practices.</p>
                </div>
            </div>
        </div>
    </section>
    
    <jsp:include page="footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
