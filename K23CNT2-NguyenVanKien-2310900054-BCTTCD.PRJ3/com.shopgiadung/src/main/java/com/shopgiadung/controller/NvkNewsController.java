package com.shopgiadung.controller;

import com.shopgiadung.entity.NvkNews;
import com.shopgiadung.service.NvkNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NvkNewsController {

    @Autowired
    private NvkNewsService newsService;

    // Public API
    @GetMapping("/news")
    public List<NvkNews> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNews(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getNewsById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Không tìm thấy bài viết");
        }
    }

    // Admin API - Thêm mới (Đã bổ sung try-catch để báo lỗi chi tiết)
    @PostMapping("/admin/news")
    public ResponseEntity<?> createNews(@RequestBody NvkNews news) {
        try {
            NvkNews savedNews = newsService.saveNews(news);
            return ResponseEntity.ok(savedNews);
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console server
            // Trả về thông báo lỗi cụ thể (ví dụ: Table doesn't exist)
            return ResponseEntity.internalServerError().body("Lỗi server: " + e.getMessage());
        }
    }

    // Admin API - Cập nhật
    @PutMapping("/admin/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @RequestBody NvkNews newsDetails) {
        try {
            NvkNews news = newsService.getNewsById(id);
            news.setTitle(newsDetails.getTitle());
            news.setCategory(newsDetails.getCategory());
            news.setContent(newsDetails.getContent());
            news.setImageUrl(newsDetails.getImageUrl());

            return ResponseEntity.ok(newsService.saveNews(news));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Lỗi cập nhật: " + e.getMessage());
        }
    }

    // Admin API - Xóa
    @DeleteMapping("/admin/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        try {
            newsService.deleteNews(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Lỗi xóa: " + e.getMessage());
        }
    }
}