package com.bookstore.model;

/**
 * Demonstrates Inheritance constraint (extends Book)
 */
public class PhysicalBook extends Book {

    private double shippingWeight;

    public PhysicalBook(int id, String title, String author, int categoryId, double price, int stock, String description, double shippingWeight) {
        super(id, title, author, categoryId, price, stock, description);
        this.shippingWeight = shippingWeight;
    }

    public double getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    @Override
    public String getBookType() {
        return "PHYSICAL";
    }
}
