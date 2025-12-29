package com.shopgiadung.service;

import com.shopgiadung.dto.NvkRegisterRequest;
import com.shopgiadung.entity.NvkRole;
import com.shopgiadung.entity.NvkUser;
import com.shopgiadung.repository.NvkUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NvkUserService {

    @Autowired
    private NvkUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lấy danh sách user theo Role (Để tách Staff và Customer)
    public List<NvkUser> getUsersByRole(NvkRole role) {
        // Vì UserRepository mặc định không có findByRole, ta lọc thủ công hoặc thêm method vào Repo
        // Ở đây dùng stream filter cho đơn giản
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .collect(Collectors.toList());
    }

    public List<NvkUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<NvkUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Tạo nhân viên mới (Admin tạo)
    public NvkUser createStaff(NvkRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        NvkUser newUser = new NvkUser();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setAddress(request.getAddress());

        // Mật khẩu mặc định hoặc từ request
        String rawPassword = request.getPassword() != null ? request.getPassword() : "123456";
        newUser.setPasswordHash(passwordEncoder.encode(rawPassword));

        // Quan trọng: Set Role là STAFF (hoặc ADMIN tùy chọn)
        newUser.setRole(NvkRole.STAFF);

        return userRepository.save(newUser);
    }

    // Xóa User
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}