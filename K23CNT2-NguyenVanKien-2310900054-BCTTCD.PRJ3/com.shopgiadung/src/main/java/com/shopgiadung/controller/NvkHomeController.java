package com.shopgiadung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NvkHomeController {

    /**
     * Chuyển hướng yêu cầu gốc "/" đến file index.html
     */
    @GetMapping("/")
    public String serveIndexPage() {
        // Giả sử bạn đặt index.html trong src/main/resources/static/
        // Nếu không, bạn cần dùng Thymeleaf hoặc cấu hình ViewResolver.
        // Hiện tại, ta sẽ dùng redirect đến file tĩnh.
        return "redirect:/index.html";
    }
}