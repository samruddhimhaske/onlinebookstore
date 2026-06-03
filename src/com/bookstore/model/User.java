package com.bookstore.model;

/**
 * Demonstrates Inheritance constraint (extends Person)
 */
public class User extends Person {
    
    private String password;
    private boolean isActive;

    public User() {
        super();
    }

    public User(int id, String name, String email, String password, String phone, String address, boolean isActive) {
        super(id, name, email, phone, address);
        this.password = password;
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String getRole() {
        return "USER";
    }
}
