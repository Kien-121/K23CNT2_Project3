package com.shopgiadung.entity;

// Enum cho trạng thái đơn hàng
public enum NvkOrderStatus {
    PENDING,        // Chờ xử lý
    PROCESSING,     // Đang xử lý
    SHIPPED,        // Đã giao cho bên vận chuyển
    COMPLETED,      // Hoàn thành
    CANCELLED       // Hủy
}