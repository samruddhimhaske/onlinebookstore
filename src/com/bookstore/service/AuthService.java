package com.bookstore.service;

import com.bookstore.dao.UserDAO;
import com.bookstore.exception.InvalidLoginException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.model.User;

public class AuthService {
    
    private final UserDAO userDAO;
    
    // Logically storing active session user
    public static User currentUser = null;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String email, String password) throws UserNotFoundException, InvalidLoginException {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Email and password cannot be empty.");
        }
        
        User user = userDAO.login(email, password);
        if (user != null) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public boolean register(String name, String email, String password, String phone, String address, String role) {
        // Basic input validation
        if (name.length() < 3 || password.length() < 6) {
            throw new IllegalArgumentException("Name must be 3+ chars and Password 6+ chars.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password); // In real app, hash this
        newUser.setPhone(phone);
        newUser.setAddress(address);
        // Using polymorphism / inheritance logic slightly loosely here just via strings for simplicity 
        
        return userDAO.register(newUser);
    }
    
    public void logout() {
        currentUser = null;
    }
}
