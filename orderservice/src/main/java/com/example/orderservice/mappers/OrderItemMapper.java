package com.example.orderservice.mappers;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.dtos.OrderItemDTO;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem dtoToEntity(OrderItemDTO orderItemDTO) {
        OrderItem result = new OrderItem();
//        result.setOrder(orderItemDTO.getOrderId());
        result.setQuantity(orderItemDTO.getQuantity());
        result.setProductId(orderItemDTO.getProductId());
        return result;
    }

    public OrderDTO entityToDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        result.setUserId(order.getUserId());
        result.setStatus(order.getStatus());
//        HashSet<OrderItemDTO> orderItems = (HashSet<OrderItemDTO>) order.getProducts().stream()
//                .map(orderMap -> {
//                    OrderItemDTO orderItem = new OrderItemDTO();
//                    orderItem.setProductId(orderMap.getProductId());
//                    orderItem.setQuantity(orderItem.getQuantity());
//                    orderItem.setOrder(orderItem.getOrder());
//                    return orderItem;
//                })
//                .collect(Collectors.toSet());
//        result.setProducts(orderItems);
        return result;
    }

    public OrderItemMapper() {
    }
}
