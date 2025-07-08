import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;

public class RestaurantOrderSystem {
    static Scanner sc = new Scanner(System.in);
    static final String DB_URL = "jdbc:mysql://localhost:3306/unisoft";
    static final String USER = "root";
    static final String PASS = "Stark@d200";

    static String currentUserRole = "";

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String RESET = "\u001B[0m";

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        login();

        while (true) {
            printFancyHeader();
            System.out.println(CYAN + getGreeting() + RESET);
            System.out.println(CYAN + "1. Search Menu" + RESET);
            System.out.println(CYAN + "2. Place New Order" + RESET);
            System.out.println(CYAN + "3. View Receipt" + RESET);
            System.out.println(CYAN + "4. Submit Order Review" + RESET);
            if (currentUserRole.equals("admin")) {
                System.out.println(CYAN + "5. View All Orders" + RESET);
                System.out.println(CYAN + "6. Monthly Sales Report" + RESET);
                System.out.println(CYAN + "8. Update Order Status" + RESET);
                System.out.println(CYAN + "9. Top-Selling Items Report" + RESET);
                System.out.println(CYAN + "10. Reorder Previous Order" + RESET);

            }
            System.out.println(CYAN + "7. Exit" + RESET);
            System.out.print(YELLOW + "\nEnter your choice: " + RESET);
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> searchMenu();
                case 2 -> placeOrder();
                case 3 -> viewReceipt();
                case 4 -> submitReview();
                case 5 -> {
                    if (currentUserRole.equals("admin"))
                        viewAllOrders();
                }
                case 6 -> {
                    if (currentUserRole.equals("admin"))
                        monthlyReport();
                }
                case 7 -> exitApp();
                case 8 -> {
                    if (currentUserRole.equals("admin"))
                        updateOrderStatus();
                }
                case 9 -> {
                    if (currentUserRole.equals("admin"))
                        topSellingItems();
                }
                case 10 -> reorderPrevious();

                default -> System.out.println(RED + "Invalid choice." + RESET);
                case 11 -> {
                    if (currentUserRole.equals("admin"))
                        viewReviews();
                }
            }
        }
    }

    static void viewReviews() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = """
                        SELECT r.order_id, r.rating, r.feedback, o.customer_name
                        FROM reviews r
                        JOIN orders o ON r.order_id = o.order_id
                        ORDER BY r.id DESC LIMIT 5
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nRecent Reviews:");
            System.out.println("----------------------------------------------");
            while (rs.next()) {
                System.out.printf("Order #%d by %s\n", rs.getInt("order_id"), rs.getString("customer_name"));
                System.out.printf(" Rating: %d/5\n", rs.getInt("rating"));
                System.out.printf(" %s\n", rs.getString("feedback"));
                System.out.println("----------------------------------------------");
            }
        }
    }

    static void login() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            while (true) {
                System.out.print("Enter username: ");
                String username = sc.nextLine();
                System.out.print("Enter password: ");
                String password = sc.nextLine();

                PreparedStatement ps = conn.prepareStatement("SELECT role FROM users WHERE username=? AND password=?");
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    currentUserRole = rs.getString("role");
                    System.out.println(GREEN + "Login successful as " + currentUserRole + RESET);
                    System.out.println(GREEN + """
                              ____  _                  __      __
                             |  _ \\| |                 \\ \\    / /
                             | |_) | | ___  _   _  ___  \\ \\  / /__  _   _  ___
                             |  _ <| |/ _ \\| | | |/ _ \\  \\ \\/ / _ \\| | | |/ _ \\
                             | |_) | | (_) | |_| |  __/   \\  / (_) | |_| | (_) |
                             |____/|_|\\___/ \\__,_|\\___|    \\/ \\___/ \\__, |\\___/
                                                                     __/ |
                                                                    |___/

                            """ + RESET);

                    break;
                } else {
                    System.out.println(RED + "Invalid credentials. Try again." + RESET);
                }
            }
        }
    }

    static void reorderPrevious() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter previous Order ID to reorder: ");
            int prevOrderId = Integer.parseInt(sc.nextLine());

            // Check if order exists
            PreparedStatement psCheck = conn.prepareStatement("SELECT customer_name FROM orders WHERE order_id = ?");
            psCheck.setInt(1, prevOrderId);
            ResultSet rsCheck = psCheck.executeQuery();
            if (!rsCheck.next()) {
                System.out.println(RED + "Order ID not found." + RESET);
                return;
            }

            String customerName = rsCheck.getString("customer_name");

            // Copy order_items and calculate total
            PreparedStatement psItems = conn
                    .prepareStatement("SELECT item_id, quantity FROM order_items WHERE order_id = ?");
            psItems.setInt(1, prevOrderId);
            ResultSet rsItems = psItems.executeQuery();

            Map<Integer, Integer> items = new HashMap<>();
            double total = 0;

            while (rsItems.next()) {
                int itemId = rsItems.getInt("item_id");
                int qty = rsItems.getInt("quantity");
                items.put(itemId, qty);

                // Get price
                PreparedStatement psPrice = conn.prepareStatement("SELECT price FROM menu WHERE id = ?");
                psPrice.setInt(1, itemId);
                ResultSet rsPrice = psPrice.executeQuery();
                if (rsPrice.next())
                    total += rsPrice.getDouble("price") * qty;
            }

            double gst = total * 0.05;
            double finalAmount = total + gst;

            // Insert new order
            PreparedStatement psOrder = conn.prepareStatement(
                    "INSERT INTO orders (customer_name, total_amount, status, order_time) VALUES (?, ?, 'Pending', NOW())",
                    Statement.RETURN_GENERATED_KEYS);
            psOrder.setString(1, customerName);
            psOrder.setDouble(2, finalAmount);
            psOrder.executeUpdate();

            ResultSet rsKey = psOrder.getGeneratedKeys();
            int newOrderId = rsKey.next() ? rsKey.getInt(1) : -1;

            // Insert order items
            for (int itemId : items.keySet()) {
                PreparedStatement psItem = conn.prepareStatement(
                        "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)");
                psItem.setInt(1, newOrderId);
                psItem.setInt(2, itemId);
                psItem.setInt(3, items.get(itemId));
                psItem.executeUpdate();
            }

            int eta = new Random().nextInt(21) + 20;
            System.out.println(GREEN + "\n Reorder placed as Order ID: " + newOrderId);
            System.out.println(" ETA: " + eta + " mins" + RESET);
        }
    }

    static void topSellingItems() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = """
                    SELECT m.name AS item_name, SUM(i.quantity) AS total_sold
                    FROM order_items i
                    JOIN menu m ON i.item_id = m.id
                    GROUP BY m.name
                    ORDER BY total_sold DESC
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nTop-Selling Items:");
            System.out.println("-------------------------------------");
            System.out.printf("%-20s | %s\n", "Item Name", "Total Sold");
            System.out.println("-------------------------------------");

            while (rs.next()) {
                System.out.printf("%-20s | %d\n",
                        rs.getString("item_name"),
                        rs.getInt("total_sold"));
            }
        }
    }

    static void searchMenu() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter keyword to search menu (or press Enter to view all): ");
            String keyword = sc.nextLine().trim();

            PreparedStatement ps = keyword.isEmpty()
                    ? conn.prepareStatement("SELECT id, name, price FROM menu")
                    : conn.prepareStatement("SELECT id, name, price FROM menu WHERE name LIKE ?");
            if (!keyword.isEmpty())
                ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            System.out.println("\n Menu:");
            System.out.println("--------------------------------------");
            while (rs.next()) {
                found = true;
                System.out.printf("%d. %s - %.2f\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
            }
            System.out.println("--------------------------------------");
            if (!found)
                System.out.println(" No items found matching your search.");
        }
    }

    static void placeOrder() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter customer name: ");
            String customerName = sc.nextLine();
            System.out.print("Enter discount code (or press Enter to skip): ");
            String discountCode = sc.nextLine();

            Map<Integer, Integer> items = new HashMap<>();
            while (true) {
                System.out.print("Enter Item ID (0 to finish): ");
                int id = Integer.parseInt(sc.nextLine());
                if (id == 0)
                    break;
                System.out.print("Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                items.put(id, items.getOrDefault(id, 0) + qty);
            }

            double total = 0;
            for (int id : items.keySet()) {
                PreparedStatement ps = conn.prepareStatement("SELECT price FROM menu WHERE id = ?");
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    total += rs.getDouble("price") * items.get(id);
            }

            double discount = 0;
            if (!discountCode.isBlank()) {
                PreparedStatement ps = conn.prepareStatement("SELECT amount, percent FROM discounts WHERE code = ?");
                ps.setString(1, discountCode);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    discount = rs.getDouble("amount") + (total * rs.getDouble("percent") / 100);
                    System.out.println(GREEN + " Discount applied: ₹" + discount + RESET);
                } else {
                    System.out.println(YELLOW + " Invalid discount code. No discount applied." + RESET);
                }
            }

            double gst = total * 0.05;
            double finalAmount = total + gst - discount;

            PreparedStatement psOrder = conn.prepareStatement(
                    "INSERT INTO orders (customer_name, total_amount, status, order_time) VALUES (?, ?, 'Pending', NOW())",
                    Statement.RETURN_GENERATED_KEYS);
            psOrder.setString(1, customerName);
            psOrder.setDouble(2, finalAmount);
            psOrder.executeUpdate();

            ResultSet rsKey = psOrder.getGeneratedKeys();
            int orderId = rsKey.next() ? rsKey.getInt(1) : -1;

            for (int id : items.keySet()) {
                PreparedStatement psItem = conn.prepareStatement(
                        "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)");
                psItem.setInt(1, orderId);
                psItem.setInt(2, id);
                psItem.setInt(3, items.get(id));
                psItem.executeUpdate();
            }

            int eta = new Random().nextInt(21) + 20;
            System.out.println("\n" + CYAN + " Order Receipt");
            System.out.println(" Order ID: " + orderId);
            System.out.println(" Customer: " + customerName);
            System.out.println(" ETA: " + eta + " mins");
            System.out.println(" Total: " + String.format("%.2f", finalAmount) + RESET);
            System.out.println(GREEN + "\n Order placed successfully!" + RESET);
        }
    }

    static void viewReceipt() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Order ID: ");
            int orderId = Integer.parseInt(sc.nextLine());

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT o.customer_name, o.total_amount, o.order_time, " +
                            "m.name AS item_name, m.price, i.quantity " +
                            "FROM orders o " +
                            "JOIN order_items i ON o.order_id = i.order_id " +
                            "JOIN menu m ON i.item_id = m.id " +
                            "WHERE o.order_id = ?");
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            String customer = "", orderTime = "";
            double total = 0;
            List<String> items = new ArrayList<>();

            while (rs.next()) {
                found = true;
                customer = rs.getString("customer_name");
                orderTime = rs.getTimestamp("order_time").toLocalDateTime().toString().replace('T', ' ');
                double price = rs.getDouble("price");
                int qty = rs.getInt("quantity");
                String name = rs.getString("item_name");
                total = rs.getDouble("total_amount");

                items.add(String.format(" - %s x%d @ %.2f", name, qty, price));
            }

            if (!found) {
                System.out.println(" Order ID not found.");
                return;
            }

            System.out.println("\nReceipt");
            System.out.println("--------------------------------------------------");
            System.out.printf("Customer     : %s\n", customer);
            System.out.printf("Order Time   : %s\n", orderTime);
            System.out.println("Items Ordered:");
            for (String item : items)
                System.out.println(item);
            System.out.println("--------------------------------------------------");
            System.out.printf("Total Paid   : %.2f\n", total);
            System.out.println("--------------------------------------------------");
        }
    }

    static void submitReview() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Order ID: ");
            int orderId = Integer.parseInt(sc.nextLine());
            System.out.print("Rating (1–5): ");
            int rating = Integer.parseInt(sc.nextLine());
            System.out.print("Feedback: ");
            String feedback = sc.nextLine();

            PreparedStatement ps = conn
                    .prepareStatement("INSERT INTO reviews (order_id, rating, feedback) VALUES (?, ?, ?)");
            ps.setInt(1, orderId);
            ps.setInt(2, rating);
            ps.setString(3, feedback);
            ps.executeUpdate();

            System.out.println(GREEN + "Review submitted. Thank you!" + RESET);
        }
    }

    static void viewAllOrders() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT order_id, customer_name, total_amount, status " +
                    "FROM orders " +
                    "WHERE customer_name IS NOT NULL AND total_amount > 0 " +
                    "ORDER BY order_time DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n All Orders:");
            System.out.println("-------------------------------------------");
            System.out.printf("%-5s | %-10s | %-8s | %s\n", "ID", "Customer", "Amount", "Status");
            System.out.println("-------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d | %-10s | %-7.2f | %s\n",
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getDouble("total_amount"),
                        rs.getString("status"));
            }
        }
    }

    static void monthlyReport() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT DATE_FORMAT(order_time, '%Y-%m') AS month, SUM(total_amount) AS total FROM orders GROUP BY month ORDER BY month DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n Monthly Report:");
            System.out.println("----------------------------");
            System.out.printf("| %-9s | %-12s |\n", "Month", "Total Sales");
            System.out.println("----------------------------");

            while (rs.next()) {
                System.out.printf("| %-9s | %-11.2f |\n", rs.getString("month"), rs.getDouble("total"));
            }

            System.out.println("----------------------------");
        }
    }

    static void updateOrderStatus() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.print("Enter Order ID to update: ");
            int orderId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter new status (Pending / Preparing / Served): ");
            String newStatus = sc.nextLine().trim();

            List<String> validStatuses = List.of("Pending", "Preparing", "Served");
            if (!validStatuses.contains(newStatus)) {
                System.out.println(RED + "Invalid status. Allowed: Pending, Preparing, Served." + RESET);
                return;
            }

            PreparedStatement ps = conn.prepareStatement("UPDATE orders SET status=? WHERE order_id=?");
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            int updated = ps.executeUpdate();

            System.out.println(updated > 0
                    ? GREEN + "Order status updated successfully!" + RESET
                    : RED + "Order ID not found." + RESET);
        }
    }

    static void exitApp() {
        System.out.println("\n=======================================");
        System.out.println(" Thank you for using the system. Goodbye!");
        System.out.println("=======================================");
        System.exit(0);
    }

    static void printFancyHeader() {
        System.out.println("===================================================");
        System.out.println("            WELCOME TO FLAVORVERSE   ");
        System.out.println("        Your Gateway to Delicious Experiences");
        System.out.println("        Fast  Fresh  Friendly  Fabulous");
        System.out.println("===================================================");

    }

    static String getGreeting() {
        int hour = LocalDateTime.now().getHour();
        if (hour < 12)
            return "Good Morning ";
        if (hour < 17)
            return "Good Afternoon ";
        if (hour < 21)
            return "Good Evening ";
        return "Good Night ";
    }
}