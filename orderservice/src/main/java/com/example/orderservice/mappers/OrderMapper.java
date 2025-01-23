package com.example.orderservice.mappers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.dtos.OrderItemDTO;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderDTO entityToDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        Set<OrderItemDTO> mapList = order.getProducts().stream().map(orderItem -> {
            OrderItemDTO orderItemResult = new OrderItemDTO();
            orderItemResult.setProductId(orderItem.getProductId());
            orderItemResult.setQuantity(orderItem.getQuantity());
            return orderItemResult;
        }).collect(Collectors.toSet());
        result.setUserId(order.getUserId());
        result.setStatus(order.getStatus());
        result.setProducts(mapList);
        return result;
    }

    public Order dtoToEntity(OrderDTO orderDTO) {
        Order orderResult = new Order();
        orderResult.setUserId(orderDTO.getUserId());
        Set<OrderItem> result = orderDTO.getProducts().stream().map(orderItemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemDto.getProductId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            Order innerOrder = new Order();
            innerOrder.setStatus(orderDTO.getStatus());
            innerOrder.setUserId(orderDTO.getUserId());
            orderItem.setOrder(innerOrder);
            return orderItem;
        }).collect(Collectors.toSet());
        orderResult.setProducts(result);
        orderResult.setStatus(orderDTO.getStatus());
        return orderResult;
    }

    public OrderMapper() {
    }
}
