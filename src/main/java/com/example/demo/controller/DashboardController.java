package com.example.demo.controller;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.model.Book;
import com.example.demo.model.Order;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        List<Order> allOrders = orderRepository.findAll();
        List<Book> allBooks = bookRepository.findAll();

        DashboardDTO dto = new DashboardDTO();

        // Basic Stats
        dto.setTotalOrders(allOrders.size());
        dto.setTotalBooks(allBooks.size());
        dto.setTotalRevenue(allOrders.stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        dto.setLowStockCount(allBooks.stream()
                .filter(b -> b.getStockQuantity() != null && b.getStockQuantity() <= 5)
                .count());

        // Status Distribution
        Map<String, Long> statusMap = allOrders.stream()
                .collect(Collectors.groupingBy(o -> o.getStatus().name(), Collectors.counting()));
        dto.setOrdersByStatus(statusMap);

        // Revenue Trend (Last 7 Days)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        Map<String, BigDecimal> trendMap = new TreeMap<>();
        
        // Initialize last 7 days
        for (int i = 6; i >= 0; i--) {
            String dateKey = java.time.LocalDate.now().minusDays(i).format(formatter);
            trendMap.put(dateKey, BigDecimal.ZERO);
        }

        allOrders.stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .filter(o -> o.getOrderDate().isAfter(java.time.LocalDateTime.now().minusDays(7)))
                .forEach(o -> {
                    String dateKey = o.getOrderDate().format(formatter);
                    if (trendMap.containsKey(dateKey)) {
                        trendMap.put(dateKey, trendMap.get(dateKey).add(o.getTotalAmount()));
                    }
                });

        dto.setRevenueTrend(trendMap.entrySet().stream()
                .map(e -> new DashboardDTO.RevenuePoint(e.getKey(), e.getValue()))
                .collect(Collectors.toList()));

        // Recent Activity (Latest 5 orders)
        dto.setRecentOrders(allOrders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(5)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(dto);
    }
}
