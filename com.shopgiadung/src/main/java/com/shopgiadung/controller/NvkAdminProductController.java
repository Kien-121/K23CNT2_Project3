package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkCategory;
import com.shopgiadung.entity.NvkInventory;
import com.shopgiadung.entity.NvkProduct;
import com.shopgiadung.repository.NvkCategoryRepository;
import com.shopgiadung.repository.NvkInventoryRepository;
import com.shopgiadung.service.NvkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "*") // Cho phép mọi nguồn truy cập (tránh lỗi CORS)
public class NvkAdminProductController {

    @Autowired
    private NvkProductService productService;

    @Autowired
    private NvkCategoryRepository categoryRepository;

    @Autowired
    private NvkInventoryRepository inventoryRepository;

    // Lấy TẤT CẢ sản phẩm (Admin cần xem cả sản phẩm hết hàng/ẩn)
    @GetMapping
    public List<NvkProduct> getAllProductsForAdmin() {
        // Sử dụng phương thức getAllProducts() hiện có, có thể cần chỉnh sửa Service
        // để không lọc tồn kho nếu muốn admin xem tất cả
        return productService.getAllProducts();
    }

    // API: POST /api/admin/products - Thêm sản phẩm mới
    // Body mẫu: { "name": "Bếp từ", "listPrice": 5000000, "sellingPrice": 4500000, "category": { "id": 1 }, "mainImageUrl": "url..." }
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody NvkProduct product) {
        try {
            // Kiểm tra danh mục
            if (product.getCategory() == null || product.getCategory().getId() == null) {
                return ResponseEntity.badRequest().body("Sản phẩm phải thuộc một danh mục.");
            }

            NvkCategory category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại."));

            product.setCategory(category);
            product.setActive(true); // Mặc định kích hoạt

            // Lưu sản phẩm
            NvkProduct savedProduct = productService.saveProduct(product);

            // Tự động tạo kho hàng (Inventory) với số lượng 0
            NvkInventory inventory = new NvkInventory();
            inventory.setProduct(savedProduct);
            inventory.setStockQuantity(0); // Mặc định tồn kho là 0 khi mới tạo
            inventoryRepository.save(inventory);

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
    }

    // API: PUT /api/admin/products/{id} - Cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            NvkProduct existingProduct = productService.getProductById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm ID: " + id));

            // Cập nhật thông tin cơ bản
            if (payload.containsKey("name")) existingProduct.setName((String) payload.get("name"));
            if (payload.containsKey("brand")) existingProduct.setBrand((String) payload.get("brand"));
            if (payload.containsKey("description")) existingProduct.setDescription((String) payload.get("description"));
            if (payload.containsKey("mainImageUrl")) existingProduct.setMainImageUrl((String) payload.get("mainImageUrl"));

            // Cập nhật giá (Cần xử lý chuyển đổi kiểu dữ liệu an toàn)
            if (payload.containsKey("listPrice")) {
                existingProduct.setListPrice(new BigDecimal(payload.get("listPrice").toString()));
            }
            if (payload.containsKey("sellingPrice")) {
                existingProduct.setSellingPrice(new BigDecimal(payload.get("sellingPrice").toString()));
            }

            // Cập nhật danh mục nếu có thay đổi
            if (payload.containsKey("category")) {
                Map<String, Object> catMap = (Map<String, Object>) payload.get("category");
                Integer catId = Integer.valueOf(catMap.get("id").toString());
                NvkCategory category = categoryRepository.findById(catId)
                        .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại."));
                existingProduct.setCategory(category);
            }

            NvkProduct updatedProduct = productService.saveProduct(existingProduct);
            return ResponseEntity.ok(updatedProduct);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    // API: DELETE /api/admin/products/{id} - Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }
}