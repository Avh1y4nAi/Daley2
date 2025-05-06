<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Property - Admin Dashboard - Ghar Dalali</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/responsive.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <style>
        .property-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 30px;
        }
        
        .property-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eee;
        }
        
        .property-title {
            margin: 0;
            font-size: 1.5rem;
        }
        
        .property-image {
            width: 100%;
            height: 300px;
            border-radius: 8px;
            overflow: hidden;
            margin-bottom: 20px;
        }
        
        .property-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .property-details {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        
        .detail-group {
            margin-bottom: 20px;
        }
        
        .detail-group h3 {
            margin-top: 0;
            margin-bottom: 10px;
            font-size: 1.1rem;
            border-bottom: 1px solid #eee;
            padding-bottom: 5px;
        }
        
        .detail-item {
            display: flex;
            margin-bottom: 8px;
        }
        
        .detail-label {
            font-weight: 600;
            width: 150px;
        }
        
        .property-description {
            margin-bottom: 30px;
        }
        
        .property-description h3 {
            margin-top: 0;
            margin-bottom: 10px;
            font-size: 1.1rem;
            border-bottom: 1px solid #eee;
            padding-bottom: 5px;
        }
        
        @media screen and (max-width: 768px) {
            .property-details {
                grid-template-columns: 1fr;
            }
        }
    </style>
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
                        <div class="property-container">
                            <div class="property-header">
                                <h2 class="property-title">Property Details: ${property.propertyName}</h2>
                                <div>
                                    <a href="${pageContext.request.contextPath}/admin/properties?action=edit&propertyId=${property.propertyId}" class="btn btn-primary">Edit Property</a>
                                    <a href="${pageContext.request.contextPath}/admin/properties" class="btn btn-outline">Back to List</a>
                                </div>
                            </div>
                            
                            <div class="property-image">
                                <img src="${property.primaryImagePath != null ? property.primaryImagePath : pageContext.request.contextPath.concat('/images/property-placeholder.jpg')}" alt="${property.propertyName}">
                            </div>
                            
                            <div class="property-description">
                                <h3>Description</h3>
                                <p>${property.description}</p>
                            </div>
                            
                            <div class="property-details">
                                <div class="detail-group">
                                    <h3>Basic Information</h3>
                                    <div class="detail-item">
                                        <div class="detail-label">Property ID</div>
                                        <div>${property.propertyId}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Property Type</div>
                                        <div>${property.propertyType}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Status</div>
                                        <div>${property.status}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Price</div>
                                        <div>NPR ${property.price}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Location</div>
                                        <div>${property.location}</div>
                                    </div>
                                </div>
                                
                                <div class="detail-group">
                                    <h3>Property Details</h3>
                                    <div class="detail-item">
                                        <div class="detail-label">Bedrooms</div>
                                        <div>${property.bedrooms != null ? property.bedrooms : 'N/A'}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Bathrooms</div>
                                        <div>${property.bathrooms != null ? property.bathrooms : 'N/A'}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Size</div>
                                        <div>${property.size != null ? property.size : 'N/A'} ${property.size != null ? property.sizeUnit : ''}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Created At</div>
                                        <div>${property.createdAt}</div>
                                    </div>
                                    <div class="detail-item">
                                        <div class="detail-label">Last Updated</div>
                                        <div>${property.updatedAt != null ? property.updatedAt : 'N/A'}</div>
                                    </div>
                                </div>
                            </div>
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
