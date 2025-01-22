package com.example.orderservice.mappers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDTO entityToDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        result.setProducts(order.getProducts());
        result.setStatus(order.getStatus());
        result.setUserId(order.getUserId());
        return result;
    }

    public Order dtoToEntity(OrderDTO orderDTO) {
        Order result = new Order();
        result.setUserId(orderDTO.getUserId());
        result.setProducts(orderDTO.getProducts());
        result.setStatus(orderDTO.getStatus());
        return result;
    }

    public OrderMapper() {
    }
}
