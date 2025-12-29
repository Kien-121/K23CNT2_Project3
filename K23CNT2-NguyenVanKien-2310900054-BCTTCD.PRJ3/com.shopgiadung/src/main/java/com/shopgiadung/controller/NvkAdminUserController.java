package com.shopgiadung.controller;

import com.shopgiadung.dto.NvkRegisterRequest;
import com.shopgiadung.entity.NvkRole;
import com.shopgiadung.entity.NvkUser;
import com.shopgiadung.service.NvkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*") // Cho phép mọi nguồn truy cập (tránh lỗi CORS)
public class NvkAdminUserController {

    @Autowired
    private NvkUserService userService;

    /**
     * API: GET /api/admin/users/role/{roleName}
     * Lấy danh sách người dùng theo vai trò (STAFF, CUSTOMER, ADMIN)
     */
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<NvkUser>> getUsersByRole(@PathVariable String roleName) {
        try {
            // Chuyển chuỗi roleName thành Enum Role (không phân biệt hoa thường)
            NvkRole role = NvkRole.valueOf(roleName.toUpperCase());
            List<NvkUser> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            // Nếu roleName không hợp lệ
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * API: POST /api/admin/users/staff
     * Tạo tài khoản nhân viên mới (Admin tạo)
     */
    @PostMapping("/staff")
    public ResponseEntity<?> createStaff(@RequestBody NvkRegisterRequest request) {
        try {
            NvkUser newStaff = userService.createStaff(request);
            return new ResponseEntity<>(newStaff, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi hệ thống khi tạo nhân viên: " + e.getMessage());
        }
    }

    /**
     * API: DELETE /api/admin/users/{id}
     * Xóa tài khoản người dùng
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("Đã xóa người dùng thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi xóa người dùng: " + e.getMessage());
        }
    }
}