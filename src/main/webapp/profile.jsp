<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>My Profile - Ghar Dalali</title>
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
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile-management.css">
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
                                        <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg"
                                            alt="User Avatar">
                                    </div>
                                    <div class="user-details">
                                        <h3>${user.fullName}</h3>
                                        <p>${user.email}</p>
                                    </div>
                                </div>
                                <nav class="dashboard-nav">
                                    <ul>
                                        <li class="active"><a
                                                href="${pageContext.request.contextPath}/dashboard/profile">My
                                                Profile</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/saved-properties">Saved
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
                                    <h2>My Profile</h2>
                                    <c:if test="${empty editMode}">
                                        <button id="edit-profile-btn" class="btn btn-primary">Edit Profile</button>
                                    </c:if>
                                </div>

                                <c:if test="${not empty successMessage}">
                                    <div class="alert alert-success">
                                        ${successMessage}
                                    </div>
                                </c:if>

                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-error">
                                        ${errorMessage}
                                    </div>
                                </c:if>

                                <!-- View Profile Section -->
                                <div id="view-profile-section" class="profile-section ${editMode ? 'hidden' : ''}">
                                    <div class="profile-details">
                                        <div class="profile-item">
                                            <h4>First Name</h4>
                                            <p>${user.firstName}</p>
                                        </div>
                                        <div class="profile-item">
                                            <h4>Last Name</h4>
                                            <p>${user.lastName}</p>
                                        </div>
                                        <div class="profile-item">
                                            <h4>Email Address</h4>
                                            <p>${user.email}</p>
                                        </div>
                                        <div class="profile-item">
                                            <h4>Phone Number</h4>
                                            <p>${user.contactNumber}</p>
                                        </div>
                                        <div class="profile-item">
                                            <h4>Address</h4>
                                            <p>${user.address}</p>
                                        </div>
                                        <div class="profile-item">
                                            <h4>Member Since</h4>
                                            <p>${user.createdAt}</p>
                                        </div>
                                    </div>
                                </div>

                                <!-- Edit Profile Section -->
                                <div id="edit-profile-section" class="profile-section ${editMode ? '' : 'hidden'}">
                                    <form id="profile-form"
                                        action="${pageContext.request.contextPath}/dashboard/profile" method="post">
                                        <!-- CSRF Token -->
                                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                                        <div class="form-group">
                                            <label for="firstName">First Name <span class="required">*</span></label>
                                            <input type="text" id="firstName" name="firstName"
                                                value="${firstName != null ? firstName : user.firstName}" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="lastName">Last Name <span class="required">*</span></label>
                                            <input type="text" id="lastName" name="lastName"
                                                value="${lastName != null ? lastName : user.lastName}" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="email">Email Address</label>
                                            <input type="email" id="email" value="${user.email}" disabled>
                                            <small>Email address cannot be changed</small>
                                        </div>

                                        <div class="form-group">
                                            <label for="contactNumber">Phone Number</label>
                                            <input type="tel" id="contactNumber" name="contactNumber"
                                                value="${contactNumber != null ? contactNumber : user.contactNumber}"
                                                placeholder="10-digit phone number">
                                            <small>Format: 10 digits (e.g., 9876543210)</small>
                                        </div>

                                        <div class="form-group">
                                            <label for="address">Address</label>
                                            <textarea id="address" name="address"
                                                rows="3">${address != null ? address : user.address}</textarea>
                                        </div>

                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-primary">Save Changes</button>
                                            <button type="button" id="cancel-edit-btn"
                                                class="btn btn-secondary">Cancel</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
            <script>
                // Profile form toggle functionality
                document.addEventListener('DOMContentLoaded', function () {
                    const viewSection = document.getElementById('view-profile-section');
                    const editSection = document.getElementById('edit-profile-section');
                    const editBtn = document.getElementById('edit-profile-btn');
                    const cancelBtn = document.getElementById('cancel-edit-btn');

                    // Show edit form
                    if (editBtn) {
                        editBtn.addEventListener('click', function () {
                            viewSection.classList.add('hidden');
                            editSection.classList.remove('hidden');
                        });
                    }

                    // Cancel edit and show view
                    if (cancelBtn) {
                        cancelBtn.addEventListener('click', function () {
                            editSection.classList.add('hidden');
                            viewSection.classList.remove('hidden');
                        });
                    }

                    // Form validation
                    const profileForm = document.getElementById('profile-form');
                    if (profileForm) {
                        profileForm.addEventListener('submit', function (e) {
                            let hasError = false;
                            const firstName = document.getElementById('firstName').value.trim();
                            const lastName = document.getElementById('lastName').value.trim();
                            const contactNumber = document.getElementById('contactNumber').value.trim();

                            // Validate first name
                            if (!firstName) {
                                hasError = true;
                                alert('First name is required');
                            } else if (!/^[A-Za-z .'\\-]+$/.test(firstName)) {
                                hasError = true;
                                alert('First name contains invalid characters');
                            }

                            // Validate last name
                            if (!lastName) {
                                hasError = true;
                                alert('Last name is required');
                            } else if (!/^[A-Za-z .'\\-]+$/.test(lastName)) {
                                hasError = true;
                                alert('Last name contains invalid characters');
                            }

                            // Validate phone number if provided
                            if (contactNumber && !/^[0-9]{10}$/.test(contactNumber)) {
                                hasError = true;
                                alert('Phone number must be 10 digits');
                            }

                            if (hasError) {
                                e.preventDefault();
                            }
                        });
                    }
                });
            </script>
        </body>

        </html>