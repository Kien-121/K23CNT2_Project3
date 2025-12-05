package com.shopgiadung.dto;

import java.util.List;
import java.math.BigDecimal;

public class OrderRequest {

    // Thông tin nhận hàng (được nhập trong form checkout)
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String paymentMethod; // VD: COD, E_WALLET, BANK_TRANSFER

    // Tổng tiền (có thể được tính lại ở backend để đảm bảo an toàn)
    private BigDecimal totalAmount;

    // Chi tiết sản phẩm trong giỏ hàng
    private List<OrderItemDto> items;

    // Giả định user_id là 3 (Khách hàng 1) vì hiện chưa có chức năng đăng nhập
    private Long userId = 3L;

    // --- Getters và Setters (Cần thiết cho Spring) ---

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}