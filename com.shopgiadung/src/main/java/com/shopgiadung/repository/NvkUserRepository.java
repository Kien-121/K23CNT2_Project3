package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NvkUserRepository extends JpaRepository<NvkUser, Long> {
    // Tự động kế thừa các phương thức CRUD

    /**
     * Tìm kiếm User theo địa chỉ email (Dùng cho chức năng Đăng nhập/Xác thực)
     * Spring Data JPA sẽ tự động triển khai phương thức này.
     * @param email Địa chỉ email của người dùng
     * @return Optional<User>
     */
    Optional<NvkUser> findByEmail(String email);
}