package com.bookstore.dao;

import com.bookstore.db.DBConnection;
import com.bookstore.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {

    public boolean addToWishlist(int userId, int bookId) {
        String query = "INSERT IGNORE INTO wishlist (user_id, book_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromWishlist(int userId, int bookId) {
        String query = "DELETE FROM wishlist WHERE user_id = ? AND book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getWishlistByUser(int userId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM books b JOIN wishlist w ON b.book_id = w.book_id WHERE w.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if ("EBOOK".equals(rs.getString("type"))) {
                    books.add(new com.bookstore.model.EBook(
                        rs.getInt("book_id"), rs.getString("title"), rs.getString("author"),
                        rs.getInt("category_id"), rs.getDouble("price"), rs.getInt("stock"),
                        rs.getString("description"), 2.5
                    ));
                } else {
                    books.add(new com.bookstore.model.PhysicalBook(
                        rs.getInt("book_id"), rs.getString("title"), rs.getString("author"),
                        rs.getInt("category_id"), rs.getDouble("price"), rs.getInt("stock"),
                        rs.getString("description"), 0.5
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
