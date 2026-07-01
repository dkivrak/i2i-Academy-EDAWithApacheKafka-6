package com.i2i.kafka.producer;

public class OrderEvent {
    private String orderId;
    private String customerName;
    private String productName;
    private int quantity;
    private double totalPrice;

    public OrderEvent() {
    }

    public OrderEvent(String orderId, String customerName, String productName, int quantity, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}