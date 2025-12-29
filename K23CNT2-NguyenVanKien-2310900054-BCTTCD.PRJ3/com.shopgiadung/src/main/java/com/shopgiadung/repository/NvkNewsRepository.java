package com.shopgiadung.repository;

import com.shopgiadung.entity.NvkNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NvkNewsRepository extends JpaRepository<NvkNews, Long> {
    // Tìm kiếm tin tức theo tiêu đề (cho trang Admin)
    List<NvkNews> findByTitleContainingIgnoreCase(String title);
}