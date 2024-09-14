package in.innovateria.onlinestore.Models;

import java.util.List;

public class OrderModel {
    private String orderId;
    private String orderDate;
    private String userId;
    private String paymentMode;
    private String orderStatus;
    private List<ProductModel> cartItems;

    public OrderModel(String orderId, String orderDate, String userId, String paymentMode, String orderStatus, List<ProductModel> cartItems) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.userId = userId;
        this.paymentMode = paymentMode;
        this.orderStatus = orderStatus;
        this.cartItems = cartItems;
    }

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<ProductModel> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<ProductModel> cartItems) {
        this.cartItems = cartItems;
    }
}


