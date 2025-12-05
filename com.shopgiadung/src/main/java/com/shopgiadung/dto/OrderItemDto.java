package com.shopgiadung.dto;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long productId;
    private Integer quantity;

    // Giá tại thời điểm đặt hàng (priceAtOrder) nên được lấy từ DB ở Service,
    // nhưng ta vẫn nhận Selling Price từ Frontend để tính toán.
    private BigDecimal sellingPrice;

    // --- Getters và Setters ---

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}