package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NvkReviewRepository extends JpaRepository<NvkReview, Long> {

    // Tìm tất cả đánh giá của một sản phẩm dựa trên productId
    // Spring Data JPA tự động hiểu findByProduct_Id dựa trên tên thuộc tính trong Entity
    List<NvkReview> findByProduct_Id(Long productId);
}