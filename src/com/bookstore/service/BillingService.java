package com.bookstore.service;

import com.bookstore.model.CartItem;
import java.util.List;

/**
 * Interface demonstration for discount processing.
 */
interface DiscountApplicable {
    double applyDiscount(double totalAmount);
}

public class BillingService implements DiscountApplicable {

    private static final double GST_RATE = 0.05; // 5% GST

    public double calculateSubTotal(List<CartItem> cartItems) {
        double total = 0.0;
        for (CartItem item : cartItems) { // Collections framework iteration
            total += (item.getBook().getPrice() * item.getQuantity());
        }
        return total;
    }

    public double calculateTax(double subTotal) {
        return subTotal * GST_RATE;
    }

    @Override
    public double applyDiscount(double subTotal) {
        // Example logic: 10% discount on orders above Rs 1500
        if (subTotal > 1500) {
            return subTotal * 0.10;
        }
        return 0;
    }
    
    public double calculateFinalTotal(double subTotal) {
        double tax = calculateTax(subTotal);
        double discount = applyDiscount(subTotal);
        return (subTotal + tax) - discount;
    }
}
