package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NvkInventoryRepository extends JpaRepository<NvkInventory, Long> {

    // Tìm kiếm thông tin kho theo Product ID (cũng là khóa chính của Inventory)
    Optional<NvkInventory> findByProductId(Long productId);
}