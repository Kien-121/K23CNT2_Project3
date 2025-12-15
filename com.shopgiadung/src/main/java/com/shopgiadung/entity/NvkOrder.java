package com.shopgiadung.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class NvkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"orders", "passwordHash", "role", "hibernateLazyInitializer", "handler"})
    private NvkUser user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private NvkPaymentMethod paymentMethod;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private NvkOrderStatus orderStatus = NvkOrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NvkOrderItem> items;

    public NvkOrder() { this.orderDate = LocalDateTime.now(); }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public NvkUser getUser() { return user; }
    public void setUser(NvkUser user) { this.user = user; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
    public NvkPaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(NvkPaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public NvkOrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(NvkOrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public List<NvkOrderItem> getItems() { return items; }
    public void setItems(List<NvkOrderItem> items) { this.items = items; }
}