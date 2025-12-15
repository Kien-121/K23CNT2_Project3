package com.shopgiadung.service;

import com.shopgiadung.entity.NvkProduct;
import com.shopgiadung.entity.NvkReview;
import com.shopgiadung.entity.NvkUser;
import com.shopgiadung.repository.NvkProductRepository;
import com.shopgiadung.repository.NvkReviewRepository;
import com.shopgiadung.repository.NvkUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NvkReviewService {

    @Autowired
    private NvkReviewRepository reviewRepository;

    @Autowired
    private NvkProductRepository productRepository;

    @Autowired
    private NvkUserRepository userRepository;

    // Lấy danh sách đánh giá theo ID sản phẩm
    public List<NvkReview> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_Id(productId);
    }

    // Lấy tất cả đánh giá (cho trang Reviews chung), sắp xếp mới nhất trước
    public List<NvkReview> getAllReviews() {
        return reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    // Thêm đánh giá mới
    public NvkReview addReview(Long productId, Integer rating, String content) {
        // 1. Lấy thông tin User đang đăng nhập từ Security Context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            // Trường hợp principal là string (anonymousUser hoặc jwt subject nếu cấu hình khác)
            email = (String) principal;
        } else {
            throw new RuntimeException("Bạn cần đăng nhập để thực hiện đánh giá.");
        }

        // Tìm User trong DB
        NvkUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin người dùng."));

        // 2. Lấy thông tin Sản phẩm
        NvkProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại."));

        // 3. Tạo và lưu Review
        NvkReview review = new NvkReview();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setContent(content);

        return reviewRepository.save(review);
    }
}