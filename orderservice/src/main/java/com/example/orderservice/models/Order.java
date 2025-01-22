package com.example.orderservice.models;

import com.example.orderservice.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "Orders")
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> products;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }
}
