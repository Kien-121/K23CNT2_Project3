package com.shopgiadung.controller;

import com.shopgiadung.dto.NvkOrderRequest;
import com.shopgiadung.entity.NvkOrder;
import com.shopgiadung.service.NvkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*") // Mở rộng CORS
public class NvkOrderController {

    @Autowired
    private NvkOrderService orderService;

    /**
     * API: POST /api/orders - Tạo đơn hàng mới
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody NvkOrderRequest request) {
        try {
            NvkOrder newOrder = orderService.createOrder(request);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Lỗi logic (ví dụ: Hết hàng, Không tìm thấy user)
            e.printStackTrace(); // In lỗi ra console server để debug
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi đặt hàng: " + e.getMessage());
        } catch (Exception e) {
            // Lỗi hệ thống khác (SQL, NullPointer...)
            e.printStackTrace(); // In lỗi ra console server để debug
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    /**
     * API: GET /api/orders - Lấy tất cả đơn hàng
     */
    @GetMapping
    public ResponseEntity<List<NvkOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * API: PUT /api/orders/{id}/status - Cập nhật trạng thái đơn hàng (Dành cho Admin)
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            String status = payload.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().body("Thiếu trạng thái (status)");
            }
            NvkOrder updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}