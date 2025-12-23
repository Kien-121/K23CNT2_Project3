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
@CrossOrigin(origins = "*") // Cho phép mọi nguồn truy cập (tránh lỗi CORS)
public class NvkOrderController {

    @Autowired
    private NvkOrderService orderService;

    /**
     * API: POST /api/orders
     * Tạo đơn hàng mới (Dành cho Khách hàng)
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody NvkOrderRequest request) {
        try {
            NvkOrder newOrder = orderService.createOrder(request);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            e.printStackTrace(); // In lỗi ra console để debug
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi đặt hàng: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    /**
     * API: GET /api/orders
     * Lấy tất cả đơn hàng (Dành cho Admin)
     */
    @GetMapping
    public ResponseEntity<List<NvkOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * API: GET /api/orders/my-orders
     * Lấy danh sách đơn hàng của người dùng đang đăng nhập (Dành cho Khách hàng)
     */
    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders() {
        try {
            List<NvkOrder> myOrders = orderService.getMyOrders();
            return ResponseEntity.ok(myOrders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống.");
        }
    }

    /**
     * API: PUT /api/orders/{id}/status
     * Cập nhật trạng thái đơn hàng (Dành cho Admin hoặc Khách hàng muốn hủy đơn)
     * Body: { "status": "PROCESSING" } hoặc { "status": "CANCELLED" }
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
            return ResponseEntity.badRequest().body("Lỗi cập nhật: " + e.getMessage());
        }
    }
}