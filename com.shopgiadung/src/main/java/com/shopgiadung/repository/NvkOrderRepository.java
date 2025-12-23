package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NvkOrderRepository extends JpaRepository<NvkOrder, Long> {
    // Tìm đơn hàng của một người dùng cụ thể, sắp xếp mới nhất lên đầu
    List<NvkOrder> findByUser_IdOrderByOrderDateDesc(Long userId);
}