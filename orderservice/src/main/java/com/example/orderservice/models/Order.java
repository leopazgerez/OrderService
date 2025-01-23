package com.example.orderservice.models;

import com.example.orderservice.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "Orders")
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> products = new HashSet<>();
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

    public Set<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderItem> products) {
        this.products = products;
    }
}
