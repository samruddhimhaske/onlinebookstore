package com.bookstore.model;

import java.util.Date;

public class Order {
    private int orderId;
    private int userId;
    private Date orderDate;
    private double totalAmount;
    private double taxAmount;
    private String status;
    private int deliveryDays;
    private String paymentMethod;
    private String orderDetails;

    public Order() {}

    public Order(int orderId, int userId, Date orderDate, double totalAmount, double taxAmount, String status, int deliveryDays, String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.status = status;
        this.deliveryDays = deliveryDays;
        this.paymentMethod = paymentMethod;
        this.orderDetails = "";
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getDeliveryDays() { return deliveryDays; }
    public void setDeliveryDays(int deliveryDays) { this.deliveryDays = deliveryDays; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getOrderDetails() { return orderDetails; }
    public void setOrderDetails(String orderDetails) { this.orderDetails = orderDetails; }
}
