package com.shopgiadung.service;

import com.shopgiadung.dto.NvkLoginRequest;
import com.shopgiadung.dto.NvkRegisterRequest;
import com.shopgiadung.entity.NvkUser;
import com.shopgiadung.entity.NvkRole;
import com.shopgiadung.repository.NvkUserRepository;
import com.shopgiadung.util.NvkJwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Lớp này cần được IDE tìm thấy
import org.springframework.stereotype.Service;

@Service
public class NvkAuthService {

    @Autowired
    private NvkUserRepository userRepository;

    // Lớp này cần được inject sau khi định nghĩa trong SecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NvkJwtUtils jwtUtils;

    /**
     * Xử lý logic đăng ký người dùng mới
     */
    public NvkUser registerUser(NvkRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được đăng ký!");
        }

        NvkUser newUser = new NvkUser();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setAddress(request.getAddress());

        // Mã hóa mật khẩu trước khi lưu
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPasswordHash(hashedPassword);

        newUser.setRole(NvkRole.CUSTOMER);

        return userRepository.save(newUser);
    }

    /**
     * Xử lý logic đăng nhập
     * @param request DTO chứa email và mật khẩu
     * @return String JWT Token nếu đăng nhập thành công
     */
    public String loginUser(NvkLoginRequest request) {
        NvkUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại."));

        // 2. So sánh mật khẩu đã mã hóa
        if (passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            // Trả về JWT Token, truyền đối tượng User để lấy ID và Role
            return jwtUtils.generateToken(user);
        } else {
            throw new RuntimeException("Mật khẩu không chính xác.");
        }
    }
}