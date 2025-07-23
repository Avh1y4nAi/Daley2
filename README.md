# Ghar Dalali - Real Estate Platform

Ghar Dalali is a full-featured, modern web application designed to streamline the process of buying, selling, and renting properties in Nepal. It serves as a comprehensive real estate platform connecting property owners, buyers, and tenants through an intuitive and secure interface. The project is built with Java Servlets and JSP, following a robust MVC-like architecture.

This project demonstrates a deep understanding of core Java web technologies, database management, and application security, making it a perfect showcase of full-stack development capabilities.

## ✨ Features

Ghar Dalali offers a rich set of features for both regular users and administrators, ensuring a seamless experience for all parties involved in the real estate market.

### 👤 User Features

  * **Authentication**: Secure user registration and login system with password hashing (BCrypt). Includes a "Forgot Password" feature with token-based recovery.
  * **Property Listings**: Browse a comprehensive list of properties with detailed information, images, and pricing.
  * **Advanced Search & Filtering**: Powerful search functionality to find properties by keyword, type, price range, number of bedrooms/bathrooms, and status.
  * **User Dashboard**: A personalized dashboard for users to manage their profile, saved properties, applications, and reviews.
  * **Saved Properties**: Users can save their favorite properties for easy access later.
  * **Property Applications**: Users can apply for properties directly through the platform.
  * **Profile Management**: Users can update their personal information and upload a profile picture.

### 🛠️ Admin Features

  * **Admin Dashboard**: A central dashboard providing key statistics such as total users, properties, applications, and reviews.
  * **User Management**: Full CRUD (Create, Read, Update, Delete) functionality for managing all users on the platform.
  * **Property Management**: Admins can add, view, edit, and delete property listings, including uploading multiple images.
  * **Application Management**: Admins can review, approve, reject, and delete user applications for properties.
  * **Review Management**: Admins can view and delete user-submitted reviews to maintain content quality.
  * **Secure Admin Area**: Role-based access control ensures that only authenticated administrators can access the admin panel.

### 🛡️ Security Features

  * **Password Hashing**: Utilizes the BCrypt algorithm to securely hash and store user passwords.
  * **CSRF Protection**: Implements CSRF tokens to protect against cross-site request forgery attacks on all state-changing requests.
  * **Input Sanitization**: All user inputs are sanitized to prevent Cross-Site Scripting (XSS) attacks.
  * **Authentication Filters**: Secure filters are used to protect user and admin routes, ensuring proper authorization.
  * **Secure Headers**: Sets various HTTP security headers like `X-Frame-Options`, `X-XSS-Protection`, and `Content-Security-Policy` to enhance application security.

## 🚀 Technologies Used

This project is built using a robust and reliable technology stack:

  * **Backend**: Java, Java Servlets, JSP
  * **Frontend**: HTML, CSS, JavaScript
  * **Database**: MySQL
  * **Build Tool**: Apache Maven (inferred from `.gitignore`)
  * **Server**: Apache Tomcat (or any other servlet container)
  * **Libraries**:
      * **Jakarta EE**: For Servlets and JSP.
      * **Jakarta Standard Tag Library (JSTL)**: For cleaner JSP code.
      * **BCrypt**: For secure password hashing.
      * **MySQL Connector/J**: For database connectivity.

## 🏁 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

  * JDK 11 or higher
  * Apache Maven
  * Apache Tomcat 9 or higher
  * MySQL Server

### Installation

1.  **Clone the repository**

    ```sh
    git clone https://github.com/your-username/ghar-dalali.git
    cd ghar-dalali
    ```

2.  **Database Setup**

      * Create a new database in MySQL named `ghar_dalali_new`.
      * Execute the `database/daley.sql` script to create the tables and insert sample data.
      * Update the database credentials in `src/main/java/com/ghardalali/util/DBUtil.java` if they differ from the defaults (user: `root`, password: `""`).

3.  **Build the Project**

      * Use Maven to build the project. This will generate a `.war` file in the `target` directory.
        ```sh
        mvn clean install
        ```

4.  **Deploy to Tomcat**

      * Copy the generated `.war` file (e.g., `ghar_dalali.war`) from the `target` directory to the `webapps` directory of your Tomcat installation.
      * Start the Tomcat server.

5.  **Access the Application**

      * Open your web browser and navigate to `http://localhost:8080/ghar_dalali/` (the context path may vary based on your configuration).

## 🗄️ Database Schema

The database consists of several interconnected tables to manage users, properties, applications, and more.

  * `users`: Stores user information, credentials, and roles.
  * `properties`: Contains all property listings and their details.
  * `property_images`: Stores paths to property images, with a flag for the primary image.
  * `saved_properties`: Tracks which properties users have saved.
  * `applications`: Manages user applications for properties, including their status (Pending, Approved, Rejected).
  * `reviews`: Stores user reviews and ratings for properties.
  * And more, including tables for payments and contact messages.
