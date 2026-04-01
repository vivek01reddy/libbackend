package com.example.demo.dto;

import com.example.demo.model.Order;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardDTO {
    private BigDecimal totalRevenue;
    private long totalOrders;
    private long totalBooks;
    private long lowStockCount;
    private Map<String, Long> ordersByStatus;
    private List<RevenuePoint> revenueTrend;
    private List<Order> recentOrders;

    public static class RevenuePoint {
        private String date;
        private BigDecimal amount;

        public RevenuePoint(String date, BigDecimal amount) {
            this.date = date;
            this.amount = amount;
        }

        public String getDate() { return date; }
        public BigDecimal getAmount() { return amount; }
    }

    // Getters and Setters
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }

    public long getTotalBooks() { return totalBooks; }
    public void setTotalBooks(long totalBooks) { this.totalBooks = totalBooks; }

    public long getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(long lowStockCount) { this.lowStockCount = lowStockCount; }

    public Map<String, Long> getOrdersByStatus() { return ordersByStatus; }
    public void setOrdersByStatus(Map<String, Long> ordersByStatus) { this.ordersByStatus = ordersByStatus; }

    public List<RevenuePoint> getRevenueTrend() { return revenueTrend; }
    public void setRevenueTrend(List<RevenuePoint> revenueTrend) { this.revenueTrend = revenueTrend; }

    public List<Order> getRecentOrders() { return recentOrders; }
    public void setRecentOrders(List<Order> recentOrders) { this.recentOrders = recentOrders; }
}
