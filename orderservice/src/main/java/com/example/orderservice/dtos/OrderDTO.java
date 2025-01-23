package com.example.orderservice.dtos;

import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.models.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;
    private Long userId;
    private Set<OrderItemDTO> products = new HashSet<>();
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

    public Set<OrderItemDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderItemDTO> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
