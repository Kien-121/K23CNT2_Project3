package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkInventory;
import com.shopgiadung.service.NvkInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/inventory")
@CrossOrigin(origins = "*") // Cho phép Frontend truy cập
public class NvkInventoryController {

    @Autowired
    private NvkInventoryService inventoryService;

    // GET /api/admin/inventory - Lấy danh sách tồn kho
    @GetMapping
    public List<NvkInventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    // PUT /api/admin/inventory/{productId} - Cập nhật số lượng tồn kho
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateStock(@PathVariable Long productId, @RequestBody Map<String, Integer> payload) {
        try {
            Integer newQuantity = payload.get("stockQuantity");
            if (newQuantity == null || newQuantity < 0) {
                return ResponseEntity.badRequest().body("Số lượng không hợp lệ.");
            }

            NvkInventory updatedInventory = inventoryService.updateStock(productId, newQuantity);
            return ResponseEntity.ok(updatedInventory);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi cập nhật kho: " + e.getMessage());
        }
    }
}