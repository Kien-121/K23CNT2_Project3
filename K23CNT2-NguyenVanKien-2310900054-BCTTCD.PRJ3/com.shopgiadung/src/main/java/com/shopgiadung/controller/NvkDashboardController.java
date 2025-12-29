package com.shopgiadung.controller;

import com.shopgiadung.dto.NvkDashboardStatsDto;
import com.shopgiadung.service.NvkDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "*")
public class NvkDashboardController {

    @Autowired
    private NvkDashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<NvkDashboardStatsDto> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
}