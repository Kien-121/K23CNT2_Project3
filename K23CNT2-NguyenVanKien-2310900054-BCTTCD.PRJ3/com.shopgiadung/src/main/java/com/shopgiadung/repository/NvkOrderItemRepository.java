package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvkOrderItemRepository extends JpaRepository<NvkOrderItem, Long> {
}