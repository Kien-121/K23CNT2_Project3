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

    // PUBLIC API: Lấy danh sách tin tức
    @GetMapping("/news")
    public List<NvkNews> getAllNews() {
        return newsService.getAllNews();
    }

    // PUBLIC API: Xem chi tiết tin
    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(newsService.getNewsById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ADMIN API: Thêm mới tin tức
    @PostMapping("/admin/news")
    public ResponseEntity<NvkNews> createNews(@RequestBody NvkNews news) {
        return ResponseEntity.ok(newsService.saveNews(news));
    }

    // ADMIN API: Cập nhật tin tức
    @PutMapping("/admin/news/{id}")
    public ResponseEntity<NvkNews> updateNews(@PathVariable Long id, @RequestBody NvkNews details) {
        NvkNews news = newsService.getNewsById(id);
        news.setTitle(details.getTitle());
        news.setContent(details.getContent());
        news.setImageUrl(details.getImageUrl());
        news.setCategory(details.getCategory());
        return ResponseEntity.ok(newsService.saveNews(news));
    }

    // ADMIN API: Xóa tin tức
    @DeleteMapping("/admin/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().build();
    }
}