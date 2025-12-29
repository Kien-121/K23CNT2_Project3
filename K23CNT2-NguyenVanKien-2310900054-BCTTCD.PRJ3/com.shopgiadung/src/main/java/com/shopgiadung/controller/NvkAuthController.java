package com.shopgiadung.controller;

import com.shopgiadung.dto.NvkLoginRequest;
import com.shopgiadung.dto.NvkRegisterRequest;
import com.shopgiadung.entity.NvkOrder;
import com.shopgiadung.service.NvkAuthService;
import com.shopgiadung.repository.NvkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
// Cho phép tất cả các nguồn truy cập để tránh lỗi CORS khi phát triển
@CrossOrigin(origins = "*")
public class NvkAuthController {

    @Autowired
    private NvkAuthService authService;

    @Autowired
    private NvkOrderRepository orderRepository;

    /**
     * API: POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NvkRegisterRequest request) {
        try {
            authService.registerUser(request);

            // Trả về JSON thành công
            Map<String, String> response = new HashMap<>();
            response.put("message", "Đăng ký thành công!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // In lỗi ra Console để debug
            e.printStackTrace();

            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // In lỗi ra Console để debug
            e.printStackTrace();

            Map<String, String> error = new HashMap<>();
            error.put("message", "Lỗi hệ thống khi đăng ký: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API: POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody NvkLoginRequest request) {
        try {
            String token = authService.loginUser(request);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Đăng nhập thành công!");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            e.printStackTrace(); // In lỗi ra console

            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console

            Map<String, String> error = new HashMap<>();
            error.put("message", "Lỗi hệ thống khi đăng nhập: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<NvkOrder> getAllOrders() {
        return orderRepository.findAll();
    }
}