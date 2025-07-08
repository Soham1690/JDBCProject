<p align="center">
  <img src="flavorverse-banner.jpg" alt="Flavorverse Logo" width="600"/>
</p>

<h1 align="center">🍽️ FLAVORVERSE - Restaurant Order Management System</h1>

<p align="center">
  A complete Java + JDBC + MySQL-based restaurant management application with a rich CLI interface, order processing, menu system, reviews, and role-based features.
</p>

---

FLAVORVERSE is a feature-rich restaurant order management system built in Java using JDBC and MySQL. It supports role-based login for Admin and Users, dynamic menu search, order placement with discounts, real-time receipt generation, and detailed sales reporting. This project demonstrates practical use of database connectivity, SQL queries, and object-oriented programming in Java.

## Author

<table>
  <tr>
    <td align="center">
      <img src="https://raw.githubusercontent.com/Soham1690/JDBCProject/master/Screenshots/Author.jpg" width="120" height="120" style="border-radius: 50%;" alt="Soham Sanyal"/>
      <br />
      <strong>Soham Sanyal</strong>
    </td>
  </tr>
</table>



<p style="margin-top: 4px;">Java Developer | Backend Engineer | Database Enthusiast</p> <p> <a href="https://github.com/Soham1690" target="_blank"> <img src="https://img.shields.io/badge/GitHub-Soham1690-181717?style=flat&logo=github" alt="GitHub"> </a> <a href="https://www.linkedin.com/in/soham6969" target="_blank"> <img src="https://img.shields.io/badge/LinkedIn-Soham%20Sanyal-0077B5?style=flat&logo=linkedin" alt="LinkedIn"> </a> <a href="mailto:sohamsanyal2@gmail.com" target="_blank"> <img src="https://img.shields.io/badge/Email-sohamsanyal2@gmail.com-D14836?style=flat&logo=gmail&logoColor=white" alt="Email"> </a> </p> </div>


## Installation
1. Make sure you have **MySQL** installed and running.
2. Create a database named `unisoft`.
3. Run the SQL script to create tables (`users`, `menu`, `orders`, `order_items`, `reviews`, `discounts`) and insert sample data.

## Running the Program

1. Make sure you have **Java JDK 8+** installed.
2. Update the database credentials in `RestaurantOrderSystem.java` if needed.
3. Compile the program:
4. Run the program:


<h2>✨ Features</h2>

<table>
  <tr>
    <td><strong>👥 User & Admin Roles</strong></td>
    <td>Separate access flows for regular users and administrators.</td>
  </tr>
  <tr>
    <td><strong>🔐 Role-based Login System</strong></td>
    <td>Secure authentication with different dashboards and privileges.</td>
  </tr>
  <tr>
    <td><strong>🍽️ Smart Menu Search</strong></td>
    <td>Instantly find dishes using keywords.</td>
  </tr>
  <tr>
    <td><strong>🛍️ Interactive Order Placement</strong></td>
    <td>Add items to your cart, apply discount codes, and confirm orders.</td>
  </tr>
  <tr>
    <td><strong>📃 Receipt Generator</strong></td>
    <td>Well-formatted receipts with tax calculation and emoji-enhanced items.</td>
  </tr>
  <tr>
    <td><strong>🔁 Reorder Past Orders</strong></td>
    <td>Quickly repeat your previous meals with a single command.</td>
  </tr>
  <tr>
    <td><strong>⭐ Review & Rate Orders</strong></td>
    <td>Submit feedback and ratings post-order to help improve service.</td>
  </tr>
  <tr>
    <td><strong>📋 View All Orders</strong></td>
    <td>Admins can see a comprehensive list of all user orders.</td>
  </tr>
  <tr>
    <td><strong>📆 Monthly Sales Report</strong></td>
    <td>Track total revenue and performance for each month.</td>
  </tr>
  <tr>
    <td><strong>⏳ Update Order Status</strong></td>
    <td>Modify the current status of orders (e.g., Preparing, Delivered).</td>
  </tr>
  <tr>
    <td><strong>🏆 Top-Selling Items Report</strong></td>
    <td>Automatically generates a report on best-selling dishes.</td>
  </tr>
  <tr>
    <td><strong>📝 Manage Customer Feedback</strong></td>
    <td>View and analyze reviews to improve food and service quality.</td>
  </tr>
</table>


<h2>🛠️ Tech Stack</h2>

<table>
  <tr>
    <td><strong>Language</strong></td>
    <td>Java</td>
  </tr>
  <tr>
    <td><strong>Database</strong></td>
    <td>MySQL</td>
  </tr>
  <tr>
    <td><strong>Connectivity</strong></td>
    <td>JDBC</td>
  </tr>
  <tr>
    <td><strong>IDE</strong></td>
    <td>Visual Studio Code</td>
  </tr>
  <tr>
    <td><strong>Version Control</strong></td>
    <td>Git & GitHub</td>
  </tr>
  <tr>
    <td><strong>Extras</strong></td>
    <td>Emoji Support, Receipt Formatting, Modular OOP Design</td>
  </tr>
</table>


## 📝 Quick Notes
🧾 Requires mysql-connector-j JAR in your classpath for JDBC connectivity.

🧠 Make sure your MySQL port (default: 3306) is open and not blocked by firewall.

🖼️ GUI/CLI-based interactive terminal – runs in console, no external UI needed.


## 🚀 Setup Instructions
Follow these steps to get FLAVORVERSE up and running on your machine:

🔧 1. Install Prerequisites
Java JDK 8+
Download and install from Oracle JDK or use OpenJDK.

MySQL Server
Download from MySQL Downloads and install. Ensure it's running locally.

🛠️ 2. Create MySQL Database
Launch MySQL CLI or Workbench.

Run the following to create the required database:

sql
Copy
Edit
CREATE DATABASE unisoft;
(Optional) Use the provided .sql file to create tables and insert sample data.

🔐 3. Configure Database Connection
Open RestaurantOrderSystem.java.

Update the DB credentials as per your setup:

java
Copy
Edit
String url = "jdbc:mysql://localhost:3306/unisoft";
String username = "your_mysql_username";
String password = "your_mysql_password";
🧪 4. Compile & Run the App
Open the project in your favorite IDE (VS Code, IntelliJ, Eclipse) or use terminal:

bash
Copy
Edit
javac RestaurantOrderSystem.java
java RestaurantOrderSystem
💡 Pro Tip: For best experience, use a terminal that supports emoji display (like VS Code Terminal or Windows Terminal).

## Usage

1. Run the program using your IDE or from the command line:
   ```bash
   javac RestaurantOrderSystem.java
   java RestaurantOrderSystem


## Project Screenshots

### Main Page
![Main Page](Screenshots/MainPage.png)

### Menu Page
![MenuPage](https://github.com/user-attachments/assets/c3b4dd12-10fa-44b4-a275-abf3f927a5ac)

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
