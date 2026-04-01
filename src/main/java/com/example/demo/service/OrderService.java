package com.example.demo.service;

import com.example.demo.dto.CheckoutRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    public Order checkout(CheckoutRequest request) {
        User user = getCurrentUser();

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PAID);

        String fullAddress = String.format("%s, %s, %s, %s %s, %s",
                request.getShippingFullName(),
                request.getShippingAddress(),
                request.getShippingCity(),
                request.getShippingState(),
                request.getShippingZip(),
                request.getShippingCountry());
        order.setShippingAddress(fullAddress);

        BigDecimal total = BigDecimal.ZERO;

        for (CheckoutRequest.CartItemDto cartItem : request.getItems()) {
            Book book = bookRepository.findById(cartItem.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + cartItem.getBookId()));

            if (book.getStockQuantity() == null || book.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for: " + book.getTitle());
            }

            // Deduct stock
            book.setStockQuantity(book.getStockQuantity() - cartItem.getQuantity());
            bookRepository.save(book);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBook(book);
            item.setQuantity(cartItem.getQuantity());
            BigDecimal price = book.getPrice() != null ? book.getPrice() : BigDecimal.ZERO;
            item.setPriceAtTimeOfPurchase(price);
            order.getItems().add(item);

            total = total.add(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    public List<Order> getMyOrders() {
        return orderRepository.findByUserOrderByOrderDateDesc(getCurrentUser());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
