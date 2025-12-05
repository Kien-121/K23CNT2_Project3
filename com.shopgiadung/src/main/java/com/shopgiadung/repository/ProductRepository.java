package com.shopgiadung.repository; // Đã đổi từ com.appliance.shop.repository

import com.shopgiadung.entity.Product; // Đã đổi import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA tự động cung cấp các phương thức CRUD cơ bản (save, findById, findAll, delete)

    // Ví dụ: Phương thức tìm kiếm theo tên sản phẩm
    List<Product> findByNameContainingIgnoreCase(String name);

    // Ví dụ: Phương thức tìm kiếm theo category ID
    List<Product> findByCategory_Id(Integer categoryId);
}