package com.bookstore.dao;

import com.bookstore.db.DBConnection;
import com.bookstore.model.User;
import com.bookstore.model.Admin;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.exception.InvalidLoginException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public User login(String email, String password) throws UserNotFoundException, InvalidLoginException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    if (!rs.getBoolean("is_active")) {
                        throw new InvalidLoginException("Your account has been blocked by an Administrator.");
                    }
                    String role = rs.getString("role");
                    if ("ADMIN".equals(role)) {
                        return new User(rs.getInt("user_id"), rs.getString("name"),
                                rs.getString("email"), rs.getString("password"),
                                rs.getString("phone"), rs.getString("address"), true) {
                            @Override
                            public String getRole() {
                                return "ADMIN";
                            }
                        };
                    } else {
                        return new User(rs.getInt("user_id"), rs.getString("name"),
                                rs.getString("email"), rs.getString("password"),
                                rs.getString("phone"), rs.getString("address"), true);
                    }
                } else {
                    throw new InvalidLoginException("Incorrect password for email: " + email);
                }
            } else {
                throw new UserNotFoundException("No user found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(User user) {
        String query = "INSERT INTO users (name, email, password, phone, address, role, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());
            stmt.setBoolean(7, true);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                users.add(new User(rs.getInt("user_id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("phone"), rs.getString("address"), rs.getBoolean("is_active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUserStatus(int userId, boolean isActive) {
        String query = "UPDATE users SET is_active = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, isActive);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ? AND role != 'ADMIN'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserProfile(User user) {
        String query = "UPDATE users SET name=?, phone=?, address=? WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getAddress());
            stmt.setInt(4, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
