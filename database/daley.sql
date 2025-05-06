-- Drop database if exists to start fresh
DROP DATABASE IF EXISTS ghar_dalali;
CREATE DATABASE ghar_dalali;
USE ghar_dalali;

-- Create users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    contact_number VARCHAR(20),
    address TEXT,
    user_role ENUM('ADMIN', 'USER') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_user_role (user_role)
);

-- Create properties table
CREATE TABLE properties (
    property_id INT AUTO_INCREMENT PRIMARY KEY,
    property_name VARCHAR(100) NOT NULL,
    property_type ENUM('APARTMENT', 'HOUSE', 'VILLA', 'COMMERCIAL', 'LAND') NOT NULL,
    description TEXT,
    location VARCHAR(255) NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    bedrooms INT,
    bathrooms INT,
    size DECIMAL(10, 2),
    size_unit VARCHAR(10) DEFAULT 'sq.ft',
    status ENUM('FOR_SALE', 'FOR_RENT', 'SOLD', 'RENTED') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_property_type (property_type),
    INDEX idx_location (location),
    INDEX idx_status (status),
    INDEX idx_price (price)
);

-- Create property_images table
CREATE TABLE property_images (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    property_id INT NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (property_id) REFERENCES properties(property_id) ON DELETE CASCADE,
    INDEX idx_property_id (property_id)
);

-- Create saved_properties table to track user's saved properties
CREATE TABLE saved_properties (
    saved_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    property_id INT NOT NULL,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES properties(property_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_property (user_id, property_id),
    INDEX idx_user_id (user_id)
);

-- Create applications table to track user's property applications
CREATE TABLE applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    property_id INT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED') DEFAULT 'PENDING',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES properties(property_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

-- Create reviews table for user property reviews
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    property_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    review_text TEXT,
    reviewed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES properties(property_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_property_review (user_id, property_id),
    INDEX idx_user_id (user_id),
    INDEX idx_property_id (property_id)
);

-- Create services table for checkout items
CREATE TABLE services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create cart table for user's shopping cart
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    service_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_service (user_id, service_id),
    INDEX idx_user_id (user_id)
);

-- Create payments table
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    property_id INT,
    amount DECIMAL(12, 2) NOT NULL,
    status ENUM('COMPLETED', 'PROCESSING', 'FAILED') NOT NULL,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    receipt_path VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES properties(property_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

-- Create contact_messages table for contact form submissions
CREATE TABLE contact_messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    subject VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    INDEX idx_email (email),
    INDEX idx_is_read (is_read)
);

-- Insert sample data for testing
-- Sample users
INSERT INTO users (first_name, last_name, email, password, contact_number, address, user_role)
VALUES 
('Admin', 'User', 'admin@ghardalali.com', '$2a$10$XFE0rQyZ5GFIgc7LsZ1XYOv8KJyZi7kdg5PuXUlGgYsXbIITHSJJ2', '9876543210', '123 Admin Street', 'ADMIN'),
('John', 'Doe', 'john@example.com', '$2a$10$XFE0rQyZ5GFIgc7LsZ1XYOv8KJyZi7kdg5PuXUlGgYsXbIITHSJJ2', '1234567890', '456 User Avenue', 'USER');

-- Sample properties
INSERT INTO properties (property_name, property_type, description, location, price, bedrooms, bathrooms, size, status)
VALUES 
('Luxury Apartment', 'APARTMENT', 'A beautiful luxury apartment with modern amenities', 'Kathmandu', 5000000.00, 3, 2, 1500.00, 'FOR_SALE'),
('Family House', 'HOUSE', 'Perfect family house in a quiet neighborhood', 'Pokhara', 7500000.00, 4, 3, 2200.00, 'FOR_SALE'),
('Commercial Space', 'COMMERCIAL', 'Prime commercial space in city center', 'Biratnagar', 10000000.00, NULL, 2, 3000.00, 'FOR_RENT');

-- Sample property images
INSERT INTO property_images (property_id, image_path, is_primary)
VALUES 
(1, 'images/properties/property1.jpg', TRUE),
(1, 'images/properties/property1-2.jpg', FALSE),
(2, 'images/properties/property2.jpg', TRUE),
(3, 'images/properties/property3.jpg', TRUE);

-- Sample services
INSERT INTO services (service_name, description, price, image_path)
VALUES 
('Property Inspection', 'Professional inspection of the property', 5000.00, 'images/services/inspection.jpg'),
('Legal Document Review', 'Review of all legal documents related to the property', 7500.00, 'images/services/legal.jpg'),
('Property Valuation', 'Professional valuation of the property', 10000.00, 'images/services/valuation.jpg');
