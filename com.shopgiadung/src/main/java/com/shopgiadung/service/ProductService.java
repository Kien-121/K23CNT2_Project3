package com.shopgiadung.service; // Đã đổi từ com.appliance.shop.service

import com.shopgiadung.entity.Product; // Đã đổi import
import com.shopgiadung.repository.ProductRepository; // Đã đổi import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Lưu/Cập nhật sản phẩm
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}