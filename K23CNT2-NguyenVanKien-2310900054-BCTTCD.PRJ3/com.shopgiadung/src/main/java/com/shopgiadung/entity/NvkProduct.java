package com.shopgiadung.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Thay đổi import
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class NvkProduct {

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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties("products")
    private NvkCategory category;

    // !!! QUAN TRỌNG: Cho phép hiện Inventory, nhưng bỏ qua trường 'product' bên trong Inventory
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    private NvkInventory inventory;

    // --- Constructors ---

    public NvkProduct() {}

    public NvkProduct(String name, BigDecimal sellingPrice, String mainImageUrl, NvkCategory category) {
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.listPrice = sellingPrice;
        this.mainImageUrl = mainImageUrl;
        this.category = category;
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getListPrice() { return listPrice; }
    public void setListPrice(BigDecimal listPrice) { this.listPrice = listPrice; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }
    public Boolean getActive() { return isActive; }
    public void setActive(Boolean active) { isActive = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public NvkCategory getCategory() { return category; }
    public void setCategory(NvkCategory category) { this.category = category; }

    public NvkInventory getInventory() { return inventory; }
    public void setInventory(NvkInventory inventory) { this.inventory = inventory; }
}