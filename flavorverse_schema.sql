-- Create database
CREATE DATABASE IF NOT EXISTS flavorverse;
USE flavorverse;

-- Users table (Admin & Customers)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'customer') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Menu table
CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    price DECIMAL(10,2) NOT NULL,
    available BOOLEAN DEFAULT TRUE
);

-- Orders table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    tax DECIMAL(10,2),
    discount_code VARCHAR(20),
    discount_amount DECIMAL(10,2),
    final_amount DECIMAL(10,2),
    estimated_delivery_time VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Order items (many-to-many relation between orders and menu_items)
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_id INT,
    quantity INT NOT NULL DEFAULT 1,
    item_price DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

-- Reviews table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    item_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    review_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

-- Discount codes table
CREATE TABLE discount_codes (
    code VARCHAR(20) PRIMARY KEY,
    description VARCHAR(100),
    discount_percent INT CHECK (discount_percent BETWEEN 1 AND 100),
    expiry_date DATE
);

-- Admin reports table (optional logs)
CREATE TABLE admin_reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    report_type VARCHAR(50),
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT
);

-- Insert default admin user
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'admin'); -- Replace with hashed password in production

-- Sample menu items
INSERT INTO menu_items (name, description, category, price) VALUES
('Margherita Pizza', 'Classic pizza with tomato and mozzarella', 'Pizza', 8.99),
('Veggie Burger', 'Grilled patty with lettuce and tomato', 'Burger', 6.50),
('Chocolate Milkshake', 'Rich and creamy shake', 'Beverage', 3.75);

-- Sample discount code
INSERT INTO discount_codes (code, description, discount_percent, expiry_date) VALUES
('WELCOME10', '10% off on your first order', 10, '2025-12-31');
