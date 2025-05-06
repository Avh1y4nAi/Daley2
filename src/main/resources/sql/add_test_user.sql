-- Add a test user with plain text password for testing
-- This should only be used in development environments

-- Check if the database exists, if not create it
CREATE DATABASE IF NOT EXISTS ghar_dalali_new;
USE ghar_dalali_new;

-- Check if users table exists, if not create it
CREATE TABLE IF NOT EXISTS users (
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

-- Add a test user with plain text password 'test123'
-- First check if the user already exists
INSERT INTO users (first_name, last_name, email, password, contact_number, address, user_role)
SELECT 'Test', 'User', 'test@example.com', 'test123', '9876543210', '123 Test Street', 'USER'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'test@example.com');

-- Add a test admin with plain text password 'admin123'
-- First check if the admin already exists
INSERT INTO users (first_name, last_name, email, password, contact_number, address, user_role)
SELECT 'Admin', 'User', 'admin@example.com', 'admin123', '9876543211', '456 Admin Street', 'ADMIN'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Show the users that were added
SELECT * FROM users WHERE email IN ('test@example.com', 'admin@example.com');
