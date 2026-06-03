package com.bookstore.model;

/**
 * Demonstrates Inheritance polymorphism.
 */
public class Admin extends Person {

    public Admin(int id, String name, String email, String phone, String address) {
        super(id, name, email, phone, address);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
