package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkPromotion;
import com.shopgiadung.service.NvkPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class NvkPromotionController {

    @Autowired
    private NvkPromotionService promotionService;

    // --- API DÀNH CHO KHÁCH HÀNG (PUBLIC) ---
    // GET /api/promotions
    @GetMapping("/api/promotions")
    public List<NvkPromotion> getPublicPromotions() {
        // Có thể thêm logic lọc chỉ lấy voucher còn hạn, còn lượt dùng...
        // Tạm thời trả về tất cả
        return promotionService.getAllPromotions();
    }

    // --- API DÀNH CHO ADMIN ---

    // GET /api/admin/promotions
    @GetMapping("/api/admin/promotions")
    public List<NvkPromotion> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    // GET /api/admin/promotions/{id}
    @GetMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> getPromotionById(@PathVariable Long id) {
        return promotionService.getPromotionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/admin/promotions - Tạo mới
    @PostMapping("/api/admin/promotions")
    public ResponseEntity<?> createPromotion(@RequestBody NvkPromotion promotion) {
        try {
            NvkPromotion saved = promotionService.savePromotion(promotion);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/admin/promotions/{id} - Cập nhật
    @PutMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable Long id, @RequestBody NvkPromotion promotionDetails) {
        return promotionService.getPromotionById(id)
                .map(existing -> {
                    existing.setCouponCode(promotionDetails.getCouponCode());
                    existing.setPromoType(promotionDetails.getPromoType());
                    existing.setValue(promotionDetails.getValue());
                    existing.setStartDate(promotionDetails.getStartDate());
                    existing.setEndDate(promotionDetails.getEndDate());
                    existing.setMaxUsage(promotionDetails.getMaxUsage());

                    return ResponseEntity.ok(promotionService.savePromotion(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/admin/promotions/{id}
    @DeleteMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        try {
            promotionService.deletePromotion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi khi xóa: " + e.getMessage());
        }
    }
}