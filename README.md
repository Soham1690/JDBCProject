# FLAVORVERSE — Java + JDBC Restaurant Order System

FLAVORVERSE is a feature-rich restaurant order management system built in Java using JDBC and MySQL. It supports role-based login for Admin and Users, dynamic menu search, order placement with discounts, real-time receipt generation, and detailed sales reporting. This project demonstrates practical use of database connectivity, SQL queries, and object-oriented programming in Java.

## Features

- Role-based login for customers and admins
- Place orders from a searchable menu
- Generate detailed receipts with tax and emojis
- Apply discount codes on orders
- Estimate delivery time based on order complexity
- Allow customers to add reviews and ratings
- Admin panel for menu and order management


## Installation
1. Make sure you have **MySQL** installed and running.
2. Create a database named `unisoft`.
3. Run the SQL script to create tables (`users`, `menu`, `orders`, `order_items`, `reviews`, `discounts`) and insert sample data.

## Running the Program

1. Make sure you have **Java JDK 8+** installed.
2. Update the database credentials in `RestaurantOrderSystem.java` if needed.
3. Compile the program:
4. Run the program:


## Features

- User login with role-based access (admin and user)
- Search menu items by keyword
- Place new orders with support for discount codes
- View order receipts with detailed item list
- Submit reviews for orders
- Admin features:
  - View all orders
  - Monthly sales report
  - Update order status
  - Top-selling items report
  - Reorder previous orders
  - View recent reviews

## Technologies Used

- Java (JDK 8 or higher)
- MySQL database
- JDBC for database connectivity
- Maven or Gradle (optional, if you use build tools)
- Command-line interface for interaction


## Setup Instructions

1. **Install Java JDK** (version 8 or higher) from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or OpenJDK.

2. **Install MySQL** and set up a database named `unisoft`.

3. **Create tables** and insert initial data in the database using the provided SQL scripts (if any).

4. **Update database credentials** in the `RestaurantOrderSystem` Java file:
   - URL: `jdbc:mysql://localhost:3306/unisoft`
   - Username and Password as per your MySQL setup.

5. **Compile and run** the `RestaurantOrderSystem.java` file from your IDE or command line.

## Usage

1. Run the program using your IDE or from the command line:
   ```bash
   javac RestaurantOrderSystem.java
   java RestaurantOrderSystem


## Project Screenshots

### Main Page
![Main Page](Screenshots/MainPage.png)

### Place Order
![Place Order](Screenshots/PlaceOrder.png)

### View Receipt
![View Receipt](Screenshots/ViewReceipt.png)

### Feedback
![Feedback](Screenshots/Feedback.png)

### Reorder Previous
![Reorder Previous](Screenshots/ReorderPrevious.png)

### Status Update
![Status Update](Screenshots/StatusUpdate.png)

### Top Selling Items
![Top Selling Items](Screenshots/TopSelling.png)

### View Orders
![View Orders](Screenshots/ViewOrders.png)

### Monthly Sales
![Monthly Sales](Screenshots/MonthlySales.png)

### Exit
![Exit](Screenshots/Exit.png)
