package com.shopgiadung.service;

import com.shopgiadung.entity.NvkOrderItem;
import com.shopgiadung.repository.NvkOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NvkOrderItemService {

    @Autowired
    private NvkOrderItemRepository orderItemRepository;

    /**
     * Lấy tất cả các mục hàng trong CSDL (Chỉ dành cho Admin/Thống kê)
     * @return Danh sách tất cả OrderItem
     */
    public List<NvkOrderItem> findAllOrderItems() {
        return orderItemRepository.findAll();
    }

    /**
     * Lấy chi tiết mục hàng theo ID
     * @param id OrderItem ID
     * @return OrderItem hoặc Optional.empty()
     */
    public Optional<NvkOrderItem> findOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    // Các chức năng tạo/sửa/xóa OrderItem thường được xử lý thông qua OrderService
    // để đảm bảo tính toàn vẹn của đơn hàng chính.
}