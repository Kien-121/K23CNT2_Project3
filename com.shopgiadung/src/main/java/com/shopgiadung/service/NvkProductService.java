package com.shopgiadung.service;

import com.shopgiadung.entity.NvkProduct; // Đã đổi import thành NvkProduct
import com.shopgiadung.repository.NvkProductRepository; // Đã đổi tên Repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NvkProductService {

    @Autowired
    private NvkProductRepository productRepository; // Sử dụng NvkProductRepository

    // Lọc sản phẩm còn hàng
    private List<NvkProduct> filterInStock(List<NvkProduct> products) { // Đổi kiểu dữ liệu
        return products.stream()
                .filter(p -> p.getInventory() != null && p.getInventory().getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Lấy tất cả sản phẩm (đã lọc tồn kho)
     */
    public List<NvkProduct> getAllProducts() {
        List<NvkProduct> allProducts = productRepository.findAll();
        return filterInStock(allProducts);
    }

    /**
     * Lấy sản phẩm theo ID (có kiểm tra tồn kho)
     */
    public Optional<NvkProduct> getProductById(Long id) {
        Optional<NvkProduct> product = productRepository.findById(id);
        // Kiểm tra tồn kho trước khi trả về
        if (product.isPresent() && product.get().getInventory() != null && product.get().getInventory().getStockQuantity() > 0) {
            return product;
        }
        return Optional.empty(); // Trả về rỗng nếu hết hàng
    }

    /**
     * Lưu/Cập nhật sản phẩm (Dùng cho Admin)
     */
    public NvkProduct saveProduct(NvkProduct product) {
        return productRepository.save(product);
    }

    /**
     * Xóa sản phẩm (Dùng cho Admin)
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Tìm kiếm và Lọc sản phẩm theo Tên hoặc Category ID
     */
    public List<NvkProduct> searchAndFilterProducts(String name, Integer categoryId) {
        List<NvkProduct> foundProducts;

        if (categoryId != null) {
            // Lọc theo Category ID (dùng phương thức tự định nghĩa trong Repository)
            foundProducts = productRepository.findByCategory_Id(categoryId);
        } else if (name != null && !name.isEmpty()) {
            // Chỉ tìm kiếm theo tên
            foundProducts = productRepository.findByNameContainingIgnoreCase(name);
        } else {
            // Lấy tất cả nếu không có tham số nào
            foundProducts = productRepository.findAll();
        }

        // Luôn luôn lọc tồn kho trước khi trả về cho khách hàng
        return filterInStock(foundProducts);
    }
}