package com.example.orderservice.mappers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.dtos.OrderItemDTO;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderDTO entityToDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        result.setUserId(order.getUserId());
        result.setStatus(order.getStatus());

        Set<OrderItemDTO> mapList = order.getProducts().stream().map(orderItem -> {
            OrderItemDTO orderItemResult = new OrderItemDTO();
            orderItemResult.setProductId(orderItem.getProductId());
            orderItemResult.setQuantity(orderItem.getQuantity());
            return orderItemResult;
        }).collect(Collectors.toSet());

        result.setProducts(mapList);
        return result;
    }

    public Order dtoToEntity(OrderDTO orderDTO) {
        Order orderResult = new Order();
        orderResult.setUserId(orderDTO.getUserId());
        orderResult.setStatus(orderDTO.getStatus());
        Set<OrderItem> result = orderDTO.getProducts().stream().map(orderItemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemDto.getProductId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setOrder(orderResult);
            return orderItem;
        }).collect(Collectors.toSet());

        orderResult.setProducts(result);
        return orderResult;
    }

    public OrderMapper() {
    }
}
