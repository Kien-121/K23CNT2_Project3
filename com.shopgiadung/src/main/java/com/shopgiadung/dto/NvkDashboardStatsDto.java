package com.shopgiadung.dto;

import java.math.BigDecimal;

public class NvkDashboardStatsDto {
    private BigDecimal totalRevenue;
    private long newOrdersCount;
    private long totalProductsCount;
    private long totalCustomersCount;

    public NvkDashboardStatsDto(BigDecimal totalRevenue, long newOrdersCount, long totalProductsCount, long totalCustomersCount) {
        this.totalRevenue = totalRevenue;
        this.newOrdersCount = newOrdersCount;
        this.totalProductsCount = totalProductsCount;
        this.totalCustomersCount = totalCustomersCount;
    }

    // Getters and Setters
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public long getNewOrdersCount() { return newOrdersCount; }
    public void setNewOrdersCount(long newOrdersCount) { this.newOrdersCount = newOrdersCount; }
    public long getTotalProductsCount() { return totalProductsCount; }
    public void setTotalProductsCount(long totalProductsCount) { this.totalProductsCount = totalProductsCount; }
    public long getTotalCustomersCount() { return totalCustomersCount; }
    public void setTotalCustomersCount(long totalCustomersCount) { this.totalCustomersCount = totalCustomersCount; }
}