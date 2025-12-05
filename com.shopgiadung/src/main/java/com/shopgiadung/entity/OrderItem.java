package com.shopgiadung.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    // Liên kết với bảng Orders (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Liên kết với bảng Products (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Cần import Product entity

    private Integer quantity;

    @Column(name = "price_at_order", nullable = false)
    private BigDecimal priceAtOrder; // Giá tại thời điểm đặt hàng

    // --- Getters and Setters and Constructors ---

    public OrderItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    public void setPriceAtOrder(BigDecimal priceAtOrder) { this.priceAtOrder = priceAtOrder; }
}