package com.bookstore.dao;

import com.bookstore.db.DBConnection;
import com.bookstore.model.Order;
import com.bookstore.model.CartItem;

import java.sql.*;
import java.util.List;

public class OrderDAO {

    public Order createOrder(int userId, double totalAmount, double taxAmount, List<CartItem> items, String paymentMethod) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Transaction management

            // 1. Insert into orders table
            String initialStatus = "Cash On Delivery".equalsIgnoreCase(paymentMethod) ? "PENDING" : "PAID";
            String orderQuery = "INSERT INTO orders (user_id, total_amount, tax_amount, status, payment_method, expected_delivery_days) VALUES (?, ?, ?, ?, ?, 0)";
            PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setDouble(2, totalAmount);
            orderStmt.setDouble(3, taxAmount);
            orderStmt.setString(4, initialStatus);
            orderStmt.setString(5, paymentMethod);
            orderStmt.executeUpdate();

            ResultSet keys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (keys.next()) {
                orderId = keys.getInt(1);
            }

            // 2. Insert into order_items and update book stock
            String itemQuery = "INSERT INTO order_items (order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemQuery);
            
            // Stock update prepared statement
            String stockQuery = "UPDATE books SET stock = stock - ? WHERE book_id = ?";
            PreparedStatement stockStmt = conn.prepareStatement(stockQuery);

            for (CartItem item : items) {
                // Insert order item
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, item.getBook().getId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setDouble(4, item.getBook().getPrice());
                itemStmt.addBatch();

                // Update stock within the same transaction
                stockStmt.setInt(1, item.getQuantity());
                stockStmt.setInt(2, item.getBook().getId());
                stockStmt.addBatch();
            }
            itemStmt.executeBatch();
            stockStmt.executeBatch();
            
            // 3. Clear the user's cart
            String clearCartQuery = "DELETE ci FROM cart_items ci JOIN cart c ON ci.cart_id = c.cart_id WHERE c.user_id = ?";
            PreparedStatement clearStmt = conn.prepareStatement(clearCartQuery);
            clearStmt.setInt(1, userId);
            clearStmt.executeUpdate();

            conn.commit(); // Commit transaction

            Order newOrder = new Order();
            newOrder.setOrderId(orderId);
            newOrder.setUserId(userId);
            newOrder.setTotalAmount(totalAmount);
            newOrder.setTaxAmount(taxAmount);
            newOrder.setStatus(initialStatus);
            newOrder.setPaymentMethod(paymentMethod);
            newOrder.setDeliveryDays(0);
            
            return newOrder;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Order> getAllOrders() {
        return getOrdersFromQuery("SELECT * FROM orders ORDER BY order_date DESC");
    }

    public List<Order> getOrdersByUser(int userId) {
        return getOrdersFromQuery("SELECT * FROM orders WHERE user_id = " + userId + " ORDER BY order_date DESC");
    }

    private List<Order> getOrdersFromQuery(String query) {
        List<Order> orders = new java.util.ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setTaxAmount(rs.getDouble("tax_amount"));
                order.setStatus(rs.getString("status"));
                
                // Safely handle new columns if they don't exist yet via try-catch or assume DBPatcher worked
                try {
                	order.setDeliveryDays(rs.getInt("expected_delivery_days"));
                	order.setPaymentMethod(rs.getString("payment_method"));
                } catch (Exception ex) {
                	// fallback
                }
                
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateDeliveryDays(int orderId, int expectedDays) {
        String query = "UPDATE orders SET expected_delivery_days = ?, status = 'SHIPPED' WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, expectedDays);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
