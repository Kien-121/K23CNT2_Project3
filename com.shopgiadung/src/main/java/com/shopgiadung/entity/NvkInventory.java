package com.shopgiadung.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class NvkInventory {

    @Id
    @Column(name = "product_id")
    private Long productId;

    // Liên kết 1:1 với NvkProduct
    @OneToOne
    @MapsId // Sử dụng ID của Product làm ID của Inventory
    @JoinColumn(name = "product_id")
    // !!! QUAN TRỌNG: Cho phép hiển thị Product, nhưng bỏ qua trường 'inventory' bên trong Product để tránh vòng lặp
    @JsonIgnoreProperties({"inventory", "reviews", "category", "hibernateLazyInitializer"})
    private NvkProduct product;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- Constructors ---

    public NvkInventory() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- Getters and Setters ---

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public NvkProduct getProduct() {
        return product;
    }

    public void setProduct(NvkProduct product) {
        this.product = product;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}