package com.bookstore.dao;

import com.bookstore.db.DBConnection;
import com.bookstore.model.Book;
import com.bookstore.model.PhysicalBook;
import com.bookstore.model.EBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements Searchable<Book> {

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE is_active = TRUE";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return searchBooks("SELECT * FROM books WHERE title LIKE ? AND is_active = TRUE", "%" + title + "%");
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return searchBooks("SELECT * FROM books WHERE author LIKE ? AND is_active = TRUE", "%" + author + "%");
    }

    @Override
    public List<Book> searchByCategory(String category) {
        String query = "SELECT b.* FROM books b JOIN categories c ON b.category_id = c.category_id WHERE c.name LIKE ? AND b.is_active = TRUE";
        return searchBooks(query, "%" + category + "%");
    }

    private List<Book> searchBooks(String query, String param) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean updateStock(int bookId, int quantity) {
        String query = "UPDATE books SET stock = stock - ? WHERE book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        if ("EBOOK".equals(rs.getString("type"))) {
            return new EBook(
                rs.getInt("book_id"), rs.getString("title"), rs.getString("author"),
                rs.getInt("category_id"), rs.getDouble("price"), rs.getInt("stock"),
                rs.getString("description"), 2.5 // Dummy filesize
            );
        } else {
            return new PhysicalBook(
                rs.getInt("book_id"), rs.getString("title"), rs.getString("author"),
                rs.getInt("category_id"), rs.getDouble("price"), rs.getInt("stock"),
                rs.getString("description"), 0.5 // Dummy weight
            );
        }
    }

    public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, category_id, price, stock, language, publication, description, type, is_active) VALUES (?, ?, ?, ?, ?, 'English', 'Default', ?, ?, TRUE)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getCategoryId());
            stmt.setDouble(4, book.getPrice());
            stmt.setInt(5, book.getStock());
            stmt.setString(6, book.getDescription());
            stmt.setString(7, book.getBookType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title=?, author=?, category_id=?, price=?, stock=?, description=? WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getCategoryId());
            stmt.setDouble(4, book.getPrice());
            stmt.setInt(5, book.getStock());
            stmt.setString(6, book.getDescription());
            stmt.setInt(7, book.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE book_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> getAllBooksAdmin() {
        List<Book> books = new ArrayList<>();
        // Admin gets all active AND inactive books technically, but let's fetch all
        String query = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
