package com.bookstore.model;

/**
 * Demonstrates Inheritance constraint (extends Book)
 */
public class EBook extends Book {

    private double fileSizeMB;

    public EBook(int id, String title, String author, int categoryId, double price, int stock, String description, double fileSizeMB) {
        super(id, title, author, categoryId, price, stock, description);
        this.fileSizeMB = fileSizeMB;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public String getBookType() {
        return "EBOOK";
    }
}
