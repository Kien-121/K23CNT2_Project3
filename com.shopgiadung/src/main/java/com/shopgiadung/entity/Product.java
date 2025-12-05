package com.shopgiadung.entity; // Đã đổi từ com.appliance.shop.model

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Sử dụng Lombok để giảm boilerplate code (Getter/Setter/NoArgsConstructor)
// Nếu bạn không dùng Lombok, cần thêm các phương thức getter/setter thủ công.

@Entity
@Table(name = "products") // Ánh xạ tới bảng 'Products' đã tạo trong MySQL
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "list_price", nullable = false)
    private BigDecimal listPrice;

    @Column(name = "selling_price", nullable = false)
    private BigDecimal sellingPrice;

    private String brand;

    @Column(name = "main_image_url")
    private String mainImageUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Liên kết với bảng Categories (N:1)
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // --- Constructors, Getters, and Setters ---
    // (Cần thiết nếu không dùng Lombok)

    public Product() {}

    // Constructor tiện lợi cho việc tạo đối tượng
    public Product(String name, BigDecimal sellingPrice, String mainImageUrl, Category category) {
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.listPrice = sellingPrice; // Giả sử listPrice = sellingPrice ban đầu
        this.mainImageUrl = mainImageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}