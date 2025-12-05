package com.shopgiadung.repository;

import com.shopgiadung.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tự động kế thừa các phương thức CRUD
}