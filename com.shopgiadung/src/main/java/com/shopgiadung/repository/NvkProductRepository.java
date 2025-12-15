package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkProduct; // Sử dụng Entity mới
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NvkProductRepository extends JpaRepository<NvkProduct, Long> {
    // Tìm kiếm sản phẩm theo tên
    List<NvkProduct> findByNameContainingIgnoreCase(String name);

    // Tìm kiếm theo Category ID
    List<NvkProduct> findByCategory_Id(Integer categoryId);
}