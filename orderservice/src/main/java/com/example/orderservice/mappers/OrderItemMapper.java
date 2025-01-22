package com.example.orderservice.mappers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public Order dtoToEntity(OrderDTO orderDTO) {
        Order result = new Order();
        result.setStatus(orderDTO.getStatus());
        result.setUserId(orderDTO.getUserId());
        result.setProducts(orderDTO.getProducts());
        return result;
    }

    public OrderDTO entityToDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        result.setUserId(order.getUserId());
        result.setStatus(order.getStatus());
        result.setProducts(order.getProducts());
        return result;
    }

    public OrderItemMapper() {
    }
}
