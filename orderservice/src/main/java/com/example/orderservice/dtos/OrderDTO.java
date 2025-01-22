package com.example.orderservice.dtos;

import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.models.OrderItem;

import java.util.List;

public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItem> products;
    private OrderStatus status;

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
