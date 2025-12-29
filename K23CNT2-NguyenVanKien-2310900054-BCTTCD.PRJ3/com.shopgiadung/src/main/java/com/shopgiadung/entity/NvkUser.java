package com.shopgiadung.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// ĐẢM BẢO CHỈ IMPORT com.shopgiadung.entity.Role DƯỚI ĐÂY
// KHÔNG ĐƯỢC IMPORT javax.management.relation.Role
// (Nếu không thấy import nào, compiler sẽ tự động tìm đúng Role trong cùng package)

@Entity
@Table(name = "users")
public class NvkUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private NvkRole role; // Đảm bảo đây là com.shopgiadung.entity.Role

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- Getters and Setters and Constructors ---

    public NvkUser() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public NvkRole getRole() { return role; }
    public void setRole(NvkRole role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}