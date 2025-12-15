package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkCategory;
import com.shopgiadung.repository.NvkCategoryRepository; // Đã cập nhật Repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"http://localhost:8080", "null"}) // Cho phép Frontend truy cập
public class NvkCategoryController { // Đã đổi tên lớp

    @Autowired
    private NvkCategoryRepository categoryRepository; // Đã cập nhật Repository

    /**
     * API: GET /api/categories
     * Trả về danh sách tất cả các Danh mục sản phẩm.
     */
    @GetMapping
    public List<NvkCategory> getAllCategories() {
        // Trả về tất cả danh mục (Điện Lạnh, Thiết Bị Nhà Bếp,...)
        return categoryRepository.findAll();
    }

    // NOTE: Các API CRUD (POST, PUT, DELETE) cho Admin sẽ được thêm sau
}