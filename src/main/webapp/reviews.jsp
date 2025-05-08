<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>My Reviews - Ghar Dalali</title>
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
                                        <li><a href="${pageContext.request.contextPath}/dashboard/profile">My
                                                Profile</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/saved-properties">Saved
                                                Properties</a></li>
                                        <li><a href="${pageContext.request.contextPath}/dashboard/applications">My
                                                Applications</a></li>
                                        <li class="active"><a
                                                href="${pageContext.request.contextPath}/dashboard/reviews">My
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
                                    <h2>My Reviews</h2>
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

                                <c:if test="${empty reviews}">
                                    <div class="empty-state">
                                        <p>You haven't written any reviews yet.</p>
                                        <p>Browse properties and share your experience by writing reviews.</p>
                                        <a href="${pageContext.request.contextPath}/properties"
                                            class="btn btn-primary">Browse Properties</a>
                                    </div>
                                </c:if>

                                <c:if test="${not empty reviews}">
                                    <div class="reviews-list">
                                        <c:forEach items="${reviews}" var="review">
                                            <div class="review-card">
                                                <div class="review-header">
                                                    <div class="property-info">
                                                        <img src="${pageContext.request.contextPath}/images/property-placeholder.jpg"
                                                            alt="${review.propertyName}">
                                                        <div>
                                                            <h4>${review.propertyName}</h4>
                                                            <p>${review.propertyLocation}</p>
                                                        </div>
                                                    </div>
                                                    <div class="review-rating">
                                                        <span class="rating">${review.rating}/5</span>
                                                        <span class="date">${review.reviewDate}</span>
                                                    </div>
                                                </div>
                                                <div class="review-content">
                                                    <p>${review.reviewText}</p>
                                                </div>
                                                <div class="review-actions">
                                                    <button class="btn btn-sm edit-review-btn"
                                                        data-review-id="${review.reviewId}"
                                                        data-rating="${review.rating}"
                                                        data-review-text="${review.reviewText}">Edit</button>
                                                    <form action="${pageContext.request.contextPath}/dashboard/reviews"
                                                        method="post" style="display: inline;">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="reviewId" value="${review.reviewId}">
                                                        <button type="submit" class="btn btn-sm btn-danger"
                                                            onclick="return confirm('Are you sure you want to delete this review?')">Delete</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </section>
            </main>

            <jsp:include page="footer.jsp" />

            <!-- Edit Review Modal -->
            <div id="editReviewModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h2>Edit Review</h2>
                    <form id="editReviewForm" action="${pageContext.request.contextPath}/dashboard/reviews"
                        method="post">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" id="editReviewId" name="reviewId" value="">

                        <div class="form-group">
                            <label for="editRating">Rating</label>
                            <select id="editRating" name="rating" required>
                                <option value="5">5 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="2">2 Stars</option>
                                <option value="1">1 Star</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="editReviewText">Review</label>
                            <textarea id="editReviewText" name="reviewText" rows="5" required></textarea>
                        </div>

                        <div class="form-actions">
                            <button type="button" class="btn btn-secondary" id="cancelEditBtn">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </div>
                    </form>
                </div>
            </div>

            <script src="${pageContext.request.contextPath}/js/main.js"></script>
            <script>
                // Get the modal
                var modal = document.getElementById("editReviewModal");

                // Get the <span> element that closes the modal
                var span = document.getElementsByClassName("close")[0];

                // Get the cancel button
                var cancelBtn = document.getElementById("cancelEditBtn");

                // When the user clicks on <span> (x) or cancel, close the modal
                span.onclick = function () {
                    modal.style.display = "none";
                }

                cancelBtn.onclick = function () {
                    modal.style.display = "none";
                }

                // When the user clicks anywhere outside of the modal, close it
                window.onclick = function (event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }

                // Add event listeners to all edit buttons
                document.addEventListener('DOMContentLoaded', function () {
                    var editButtons = document.querySelectorAll('.edit-review-btn');
                    editButtons.forEach(function (button) {
                        button.addEventListener('click', function () {
                            var reviewId = this.getAttribute('data-review-id');
                            var rating = this.getAttribute('data-rating');
                            var reviewText = this.getAttribute('data-review-text');

                            document.getElementById("editReviewId").value = reviewId;
                            document.getElementById("editRating").value = rating;
                            document.getElementById("editReviewText").value = reviewText;

                            modal.style.display = "block";
                        });
                    });
                });
            </script>
        </body>

        </html>