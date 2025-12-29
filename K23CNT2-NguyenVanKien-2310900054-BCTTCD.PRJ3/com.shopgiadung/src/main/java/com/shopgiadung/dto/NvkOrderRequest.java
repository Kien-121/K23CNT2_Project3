package com.shopgiadung.dto;

import java.math.BigDecimal;
import java.util.List;

public class NvkOrderRequest {
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private List<NvkOrderItemDto> items;
    private Long userId; // Optional nếu lấy từ Token

    // Getters & Setters
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<NvkOrderItemDto> getItems() { return items; }
    public void setItems(List<NvkOrderItemDto> items) { this.items = items; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}