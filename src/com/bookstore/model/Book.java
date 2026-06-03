package com.bookstore.model;

/**
 * Demonstrates Abstraction and Encapsulation.
 */
public abstract class Book {
    private int id;
    private String title;
    private String author;
    private int categoryId;
    private double price;
    private int stock;
    private String description;
    
    public Book() {}

    public Book(int id, String title, String author, int categoryId, double price, int stock, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    // Abstract method for Polymorphism demonstration
    public abstract String getBookType();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
