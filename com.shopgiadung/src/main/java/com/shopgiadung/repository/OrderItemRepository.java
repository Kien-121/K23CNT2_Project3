package com.shopgiadung.repository;

import com.shopgiadung.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Tự động kế thừa các phương thức CRUD
}