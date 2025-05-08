<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Manage Users - Admin Dashboard - Ghar Dalali</title>
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
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                                    <h2>Manage Users</h2>
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
                                            <input type="text" placeholder="Search users...">
                                            <button class="btn btn-primary">Search</button>
                                        </div>
                                        <div class="filter-box">
                                            <select>
                                                <option value="">All Roles</option>
                                                <option value="ADMIN">Admin</option>
                                                <option value="USER">User</option>
                                            </select>
                                        </div>
                                    </div>

                                    <table class="admin-table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>Email</th>
                                                <th>Role</th>
                                                <th>Created At</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${users}" var="userItem">
                                                <tr>
                                                    <td>${userItem.userId}</td>
                                                    <td>${userItem.fullName}</td>
                                                    <td>${userItem.email}</td>
                                                    <td>${userItem.userRole}</td>
                                                    <td>${userItem.createdAt}</td>
                                                    <td class="actions">
                                                        <button class="btn-icon edit" title="Edit User"><i
                                                                class="fa-regular fa-pen-to-square"></i></button>
                                                        <form action="${pageContext.request.contextPath}/admin/users"
                                                            method="post" style="display: inline;">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="userId"
                                                                value="${userItem.userId}">
                                                            <input type="hidden" name="csrfToken"
                                                                value="${sessionScope.csrfToken}">
                                                            <button type="submit" class="btn-icon delete"
                                                                title="Delete User"
                                                                onclick="return confirm('Are you sure you want to delete this user?')"><i
                                                                    class="fa-regular fa-trash"></i></button>
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

            <!-- View User Modal -->
            <div id="viewUserModal" class="modal">
                <div class="modal-content">
                    <span class="close view-close">&times;</span>
                    <h2>View User</h2>
                    <div class="user-details">
                        <div class="user-info">
                            <p><strong>User ID:</strong> <span id="viewUserId"></span></p>
                            <p><strong>Name:</strong> <span id="viewUserName"></span></p>
                            <p><strong>Email:</strong> <span id="viewUserEmail"></span></p>
                            <p><strong>Role:</strong> <span id="viewUserRole"></span></p>
                            <p><strong>Created At:</strong> <span id="viewUserCreatedAt"></span></p>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-secondary" id="closeViewBtn">Close</button>
                            <button type="button" class="btn btn-primary" id="editFromViewBtn">Edit User</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit User Modal -->
            <div id="editUserModal" class="modal">
                <div class="modal-content">
                    <span class="close edit-close">&times;</span>
                    <h2>Edit User</h2>
                    <form id="editUserForm" action="${pageContext.request.contextPath}/admin/users" method="post">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" id="editUserId" name="userId" value="">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">

                        <div class="form-group">
                            <label for="editUserName">Name</label>
                            <input type="text" id="editUserName" name="fullName" required>
                        </div>

                        <div class="form-group">
                            <label for="editUserEmail">Email</label>
                            <input type="email" id="editUserEmail" name="email" required>
                        </div>

                        <div class="form-group">
                            <label for="editUserRole">Role</label>
                            <select id="editUserRole" name="userRole" required>
                                <option value="ADMIN">Admin</option>
                                <option value="USER">User</option>
                            </select>
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
                var editModal = document.getElementById("editUserModal");
                var viewModal = document.getElementById("viewUserModal");

                // Get the <span> elements that close the modals
                var editSpan = document.querySelector("#editUserModal .close");
                var viewSpan = document.querySelector("#viewUserModal .close");

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
                    var userId = document.getElementById("viewUserId").textContent;
                    var userName = document.getElementById("viewUserName").textContent;
                    var userEmail = document.getElementById("viewUserEmail").textContent;
                    var userRole = document.getElementById("viewUserRole").textContent;
                    openEditModal(userId, userName, userEmail, userRole);
                }

                // When the user clicks anywhere outside of the modals, close them
                window.onclick = function (event) {
                    if (event.target == editModal) {
                        editModal.style.display = "none";
                    } else if (event.target == viewModal) {
                        viewModal.style.display = "none";
                    }
                }

                // Function to open the edit modal and populate it with user data
                function openEditModal(userId, userName, userEmail, userRole) {
                    document.getElementById("editUserId").value = userId;
                    document.getElementById("editUserName").value = userName;
                    document.getElementById("editUserEmail").value = userEmail;
                    document.getElementById("editUserRole").value = userRole;
                    editModal.style.display = "block";
                }

                // Function to open the view modal and populate it with user data
                function openViewModal(userId, userName, userEmail, userRole, createdAt) {
                    document.getElementById("viewUserId").textContent = userId;
                    document.getElementById("viewUserName").textContent = userName;
                    document.getElementById("viewUserEmail").textContent = userEmail;
                    document.getElementById("viewUserRole").textContent = userRole;
                    document.getElementById("viewUserCreatedAt").textContent = createdAt;
                    viewModal.style.display = "block";
                }

                // Add event listeners to all buttons
                document.addEventListener('DOMContentLoaded', function () {
                    // Edit button event listeners
                    var editButtons = document.querySelectorAll('.edit');
                    editButtons.forEach(function (button) {
                        button.addEventListener('click', function () {
                            var row = this.closest('tr');
                            var userId = row.querySelector('td:first-child').textContent;
                            var userName = row.querySelector('td:nth-child(2)').textContent;
                            var userEmail = row.querySelector('td:nth-child(3)').textContent;
                            var userRole = row.querySelector('td:nth-child(4)').textContent;
                            openEditModal(userId, userName, userEmail, userRole);
                        });
                    });
                });
            </script>
        </body>

        </html>