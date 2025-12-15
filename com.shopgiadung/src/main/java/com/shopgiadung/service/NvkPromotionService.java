package com.shopgiadung.service;

import com.shopgiadung.entity.NvkPromotion;
import com.shopgiadung.repository.NvkPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NvkPromotionService {

    @Autowired
    private NvkPromotionRepository promotionRepository;

    public List<NvkPromotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public NvkPromotion savePromotion(NvkPromotion promotion) {
        // Kiểm tra trùng mã code nếu là tạo mới
        if (promotion.getId() == null) {
            Optional<NvkPromotion> existing = promotionRepository.findByCouponCode(promotion.getCouponCode());
            if (existing.isPresent()) {
                throw new RuntimeException("Mã giảm giá '" + promotion.getCouponCode() + "' đã tồn tại.");
            }
        }
        return promotionRepository.save(promotion);
    }

    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    public Optional<NvkPromotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }
}