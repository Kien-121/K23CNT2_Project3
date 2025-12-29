package com.shopgiadung.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class NvkReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    // Liên kết với User (Người dùng đánh giá)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"passwordHash", "role", "orders", "reviews", "hibernateLazyInitializer", "handler"})
    private NvkUser user;

    // Liên kết với NvkProduct (Sản phẩm được đánh giá)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"inventory", "category", "reviews", "hibernateLazyInitializer", "handler"})
    private NvkProduct product;

    @Column(nullable = false)
    private Integer rating; // 1 đến 5 sao

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- Constructors ---
    public NvkReview() {}

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public NvkUser getUser() { return user; }
    public void setUser(NvkUser user) { this.user = user; }

    public NvkProduct getProduct() { return product; }
    public void setProduct(NvkProduct product) { this.product = product; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}