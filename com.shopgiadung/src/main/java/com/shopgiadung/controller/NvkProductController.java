package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkProduct; // Đã đổi import thành NvkProduct
import com.shopgiadung.service.NvkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:8080", "null"}) // Cho phép frontend ở cổng 8080 truy cập
public class NvkProductController {

    @Autowired
    private NvkProductService productService;

    /**
     * API: GET /api/products
     * Lấy tất cả sản phẩm (có thể lọc theo tên hoặc danh mục)
     * Frontend sử dụng: index.html
     */
    @GetMapping
    public List<NvkProduct> getAllProducts( // Đã đổi kiểu trả về thành NvkProduct
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) Integer categoryId) {

        // Sử dụng Service mới để lọc theo tên và categoryId
        return productService.searchAndFilterProducts(name, categoryId);
    }

    /**
     * API: GET /api/products/{id}
     * Lấy chi tiết sản phẩm theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<NvkProduct> getProductById(@PathVariable Long id) { // Đã đổi kiểu trả về thành NvkProduct
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // NOTE: Các chức năng POST, PUT, DELETE đã được chuyển sang AdminProductController
}