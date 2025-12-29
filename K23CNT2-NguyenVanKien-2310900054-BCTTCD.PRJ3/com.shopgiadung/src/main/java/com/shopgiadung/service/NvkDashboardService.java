package com.shopgiadung.service;

import com.shopgiadung.dto.NvkDashboardStatsDto;
import com.shopgiadung.entity.NvkRole;
import com.shopgiadung.repository.NvkOrderRepository;
import com.shopgiadung.repository.NvkProductRepository;
import com.shopgiadung.repository.NvkUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NvkDashboardService {

    @Autowired
    private NvkOrderRepository orderRepository;

    @Autowired
    private NvkProductRepository productRepository;

    @Autowired
    private NvkUserRepository userRepository;

    public NvkDashboardStatsDto getStats() {
        // 1. Tính tổng doanh thu (giả sử tính trên tất cả đơn hàng)
        BigDecimal totalRevenue = orderRepository.findAll().stream()
                .map(order -> order.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Đếm số đơn hàng
        long newOrdersCount = orderRepository.count();

        // 3. Đếm tổng số sản phẩm
        long totalProductsCount = productRepository.count();

        // 4. Đếm tổng số khách hàng (Role = CUSTOMER)
        long totalCustomersCount = userRepository.findAll().stream()
                .filter(user -> user.getRole() == NvkRole.CUSTOMER)
                .count();

        return new NvkDashboardStatsDto(totalRevenue, newOrdersCount, totalProductsCount, totalCustomersCount);
    }
}