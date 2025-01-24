package com.example.orderservice.mappers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.dtos.OrderItemDTO;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {

    public OrderItem dtoToEntity(OrderItemDTO orderItemDTO, Order order) {
        OrderItem result = new OrderItem();
        result.setQuantity(orderItemDTO.getQuantity());
        result.setProductId(orderItemDTO.getProductId());
        result.setOrder(order);
        return result;
    }

    public OrderDTO entityToDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        result.setUserId(order.getUserId());
        result.setStatus(order.getStatus());
        Set<OrderItemDTO> orderItems = order.getProducts().stream()
                .map(orderItem -> {
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setProductId(orderItem.getProductId());
                    orderItemDTO.setQuantity(orderItem.getQuantity());
                    return orderItemDTO;
                })
                .collect(Collectors.toSet());
        result.setProducts(orderItems);
        return result;
    }

    public OrderItemMapper() {
    }
}
