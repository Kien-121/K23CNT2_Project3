package com.shopgiadung.controller; // Đã đổi từ com.appliance.shop.controller

import com.shopgiadung.entity.Product; // Đã đổi import
import com.shopgiadung.service.ProductService; // Đã đổi import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:8080", "null"}) // Thêm "null" để hỗ trợ chạy file:///
public class ProductController {

    @Autowired
    private ProductService productService;

    // API: GET /api/products
    // Lấy tất cả sản phẩm
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // API: GET /api/products/{id}
    // Lấy chi tiết sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // API: POST /api/products
    // Thêm sản phẩm mới (Cần quyền ADMIN/STAFF trong thực tế)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // API: GET /api/products/search?name=keyword
    // Tìm kiếm sản phẩm
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }
}