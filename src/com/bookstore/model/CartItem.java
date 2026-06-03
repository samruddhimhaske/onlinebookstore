package com.bookstore.model;

public class CartItem {
    private int cartItemId;
    private int cartId;
    private Book book;
    private int quantity;

    public CartItem() {}

    public CartItem(int cartItemId, int cartId, Book book, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.book = book;
        this.quantity = quantity;
    }

    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }
    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
