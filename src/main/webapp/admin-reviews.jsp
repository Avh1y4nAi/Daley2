<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Manage Reviews - Admin Dashboard - Ghar Dalali</title>
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
                                    <h2>Manage Reviews</h2>
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
                                <c:if test="${not empty sessionScope.errorMessage}">
                                    <div class="alert alert-error">
                                        <p>${sessionScope.errorMessage}</p>
                                    </div>
                                    <c:remove var="errorMessage" scope="session" />
                                </c:if>

                                <div class="admin-table-container">
                                    <div class="table-actions">
                                        <div class="search-box">
                                            <input type="text" placeholder="Search reviews...">
                                            <button class="btn btn-primary">Search</button>
                                        </div>
                                        <div class="filter-box">
                                            <select>
                                                <option value="">All Ratings</option>
                                                <option value="5">5 Stars</option>
                                                <option value="4">4 Stars</option>
                                                <option value="3">3 Stars</option>
                                                <option value="2">2 Stars</option>
                                                <option value="1">1 Star</option>
                                            </select>
                                        </div>
                                    </div>

                                    <table class="admin-table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>User</th>
                                                <th>Property</th>
                                                <th>Rating</th>
                                                <th>Review</th>
                                                <th>Date</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${reviews}" var="review">
                                                <tr>
                                                    <td>${review.reviewId}</td>
                                                    <td>${review.userName}</td>
                                                    <td>${review.propertyTitle}</td>
                                                    <td>${review.rating}</td>
                                                    <td>${review.reviewText}</td>
                                                    <td>${review.reviewedAt}</td>
                                                    <td class="actions">
                                                        <button class="btn-icon view" title="View Review">
                                                            <i class="fa-regular fa-eye"></i>
                                                        </button>
                                                        <button class="btn-icon edit" title="Edit Review">
                                                            <i class="fa-regular fa-pen-to-square"></i>
                                                        </button>
                                                        <form action="${pageContext.request.contextPath}/admin/reviews"
                                                            method="post" style="display: inline;">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="reviewId"
                                                                value="${review.reviewId}">
                                                            <input type="hidden" name="csrfToken"
                                                                value="${sessionScope.csrfToken}">
                                                            <button type="submit" class="btn-icon delete"
                                                                title="Delete Review"
                                                                onclick="return confirm('Are you sure you want to delete this review?')">
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

            <!-- View Review Modal -->
            <div id="viewReviewModal" class="modal">
                <div class="modal-content">
                    <span class="close view-close">&times;</span>
                    <h2>View Review</h2>
                    <div class="review-details">
                        <div class="review-info">
                            <p><strong>Review ID:</strong> <span id="viewReviewId"></span></p>
                            <p><strong>User:</strong> <span id="viewUserName"></span></p>
                            <p><strong>Property:</strong> <span id="viewPropertyTitle"></span></p>
                            <p><strong>Rating:</strong> <span id="viewRating"></span></p>
                            <p><strong>Date:</strong> <span id="viewDate"></span></p>
                        </div>
                        <div class="review-text">
                            <h3>Review Text:</h3>
                            <p id="viewReviewText"></p>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-secondary" id="closeViewBtn">Close</button>
                            <button type="button" class="btn btn-primary" id="editFromViewBtn">Edit Review</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit Review Modal -->
            <div id="editReviewModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <h2>Edit Review</h2>
                    <form id="editReviewForm" action="${pageContext.request.contextPath}/admin/reviews" method="post">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" id="editReviewId" name="reviewId" value="">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

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
                // Get the modals
                var editModal = document.getElementById("editReviewModal");
                var viewModal = document.getElementById("viewReviewModal");

                // Get the <span> elements that close the modals
                var editSpan = document.querySelector("#editReviewModal .close");
                var viewSpan = document.querySelector("#viewReviewModal .close");

                // Get the cancel buttons
                var cancelEditBtn = document.getElementById("cancelEditBtn");
                var closeViewBtn = document.getElementById("closeViewBtn");
                var editFromViewBtn = document.getElementById("editFromViewBtn");

                // When the user clicks on <span> (x) or cancel, close the modals
                editSpan.onclick = function () {
                    editModal.style.display = "none";
                }

                viewSpan.onclick = function () {
                    viewModal.style.display = "none";
                }

                cancelEditBtn.onclick = function () {
                    editModal.style.display = "none";
                }

                closeViewBtn.onclick = function () {
                    viewModal.style.display = "none";
                }

                editFromViewBtn.onclick = function () {
                    // Close view modal and open edit modal with the same data
                    viewModal.style.display = "none";
                    var reviewId = document.getElementById("viewReviewId").textContent;
                    var rating = document.getElementById("viewRating").textContent;
                    var reviewText = document.getElementById("viewReviewText").textContent;
                    openEditModal(reviewId, rating, reviewText);
                }

                // When the user clicks anywhere outside of the modals, close them
                window.onclick = function (event) {
                    if (event.target == editModal) {
                        editModal.style.display = "none";
                    } else if (event.target == viewModal) {
                        viewModal.style.display = "none";
                    }
                }

                // Function to open the edit modal and populate it with review data
                function openEditModal(reviewId, rating, reviewText) {
                    document.getElementById("editReviewId").value = reviewId;
                    document.getElementById("editRating").value = rating;
                    document.getElementById("editReviewText").value = reviewText;
                    editModal.style.display = "block";
                }

                // Function to open the view modal and populate it with review data
                function openViewModal(reviewId, userName, propertyTitle, rating, reviewText, date) {
                    document.getElementById("viewReviewId").textContent = reviewId;
                    document.getElementById("viewUserName").textContent = userName;
                    document.getElementById("viewPropertyTitle").textContent = propertyTitle;
                    document.getElementById("viewRating").textContent = rating;
                    document.getElementById("viewReviewText").textContent = reviewText;
                    document.getElementById("viewDate").textContent = date;
                    viewModal.style.display = "block";
                }

                // Add event listeners to all buttons
                document.addEventListener('DOMContentLoaded', function () {
                    // Edit button event listeners
                    var editButtons = document.querySelectorAll('.edit');
                    editButtons.forEach(function (button) {
                        button.addEventListener('click', function () {
                            var row = this.closest('tr');
                            var reviewId = row.querySelector('td:first-child').textContent;
                            var rating = row.querySelector('td:nth-child(4)').textContent;
                            var reviewText = row.querySelector('td:nth-child(5)').textContent;
                            openEditModal(reviewId, rating, reviewText);
                        });
                    });

                    // View button event listeners
                    var viewButtons = document.querySelectorAll('.view');
                    viewButtons.forEach(function (button) {
                        button.addEventListener('click', function () {
                            var row = this.closest('tr');
                            var reviewId = row.querySelector('td:first-child').textContent;
                            var userName = row.querySelector('td:nth-child(2)').textContent;
                            var propertyTitle = row.querySelector('td:nth-child(3)').textContent;
                            var rating = row.querySelector('td:nth-child(4)').textContent;
                            var reviewText = row.querySelector('td:nth-child(5)').textContent;
                            var date = row.querySelector('td:nth-child(6)').textContent;
                            openViewModal(reviewId, userName, propertyTitle, rating, reviewText, date);
                        });
                    });
                });
            </script>
        </body>

        </html>