package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvkCategoryRepository extends JpaRepository<NvkCategory, Integer> { // Đã đổi tên interface
    // Kế thừa các phương thức CRUD
}