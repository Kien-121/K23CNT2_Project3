package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvkOrderRepository extends JpaRepository<NvkOrder, Long> {
}