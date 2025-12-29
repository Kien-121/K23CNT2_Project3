package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkReview;
import com.shopgiadung.service.NvkReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
// Cho phép tất cả các nguồn truy cập (tránh lỗi CORS khi test localhost)
@CrossOrigin(origins = "*")
public class NvkReviewController {

    @Autowired
    private NvkReviewService reviewService;

    /**
     * API: GET /api/reviews
     * Lấy tất cả đánh giá mới nhất (cho trang reviews.html)
     */
    @GetMapping
    public List<NvkReview> getAllReviews() {
        return reviewService.getAllReviews();
    }

    /**
     * API: GET /api/reviews/product/{id}
     * Lấy đánh giá của một sản phẩm cụ thể
     */
    @GetMapping("/product/{productId}")
    public List<NvkReview> getProductReviews(@PathVariable Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }

    /**
     * API: POST /api/reviews/add
     * Thêm đánh giá mới (Cần Token đăng nhập)
     * Payload: { "productId": 1, "rating": 5, "content": "Sản phẩm tốt" }
     */
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody Map<String, Object> payload) {
        try {
            // Kiểm tra dữ liệu đầu vào
            if (payload.get("productId") == null || payload.get("rating") == null) {
                return ResponseEntity.badRequest().body("Thiếu thông tin productId hoặc rating.");
            }

            // Chuyển đổi dữ liệu từ JSON payload
            // Dùng toString() rồi parse để tránh lỗi kiểu dữ liệu (Integer vs Long trong JSON)
            Long productId = Long.valueOf(payload.get("productId").toString());
            Integer rating = Integer.valueOf(payload.get("rating").toString());
            String content = (String) payload.get("content");

            // Gọi service để thêm đánh giá
            NvkReview newReview = reviewService.addReview(productId, rating, content);

            return ResponseEntity.ok(newReview);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Dữ liệu productId hoặc rating không hợp lệ.");
        } catch (RuntimeException e) {
            // Xử lý các lỗi từ Service (User not found, Product not found...)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi hệ thống khi thêm đánh giá: " + e.getMessage());
        }
    }
}