package com.shopgiadung.controller;

import com.shopgiadung.dto.OrderRequest;
import com.shopgiadung.entity.Order;
import com.shopgiadung.service.OrderService;
import com.shopgiadung.repository.OrderRepository; // Đã thêm import cho OrderRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:8080", "null"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Đã thêm injection cho OrderRepository để có thể sử dụng findAll()
    @Autowired
    private OrderRepository orderRepository;

    /**
     * API: POST /api/orders
     * Tạo đơn hàng mới từ giỏ hàng Frontend
     * @param request OrderRequest DTO
     * @return Order đã tạo
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            Order newOrder = orderService.createOrder(request);
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Xử lý các lỗi nghiệp vụ như Product not found, Invalid payment method
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Xử lý lỗi chung
            return new ResponseEntity<>("Lỗi hệ thống khi tạo đơn hàng.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API: GET /api/orders
     * Lấy tất cả đơn hàng (Chỉ dành cho Admin/Staff trong thực tế)
     * @return Danh sách tất cả đơn hàng
     */
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}