package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NvkPromotionRepository extends JpaRepository<NvkPromotion, Long> {
    // Tìm kiếm theo mã coupon
    Optional<NvkPromotion> findByCouponCode(String couponCode);
}