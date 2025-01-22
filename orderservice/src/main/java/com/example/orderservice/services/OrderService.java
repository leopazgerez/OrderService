package com.example.orderservice.services;

import com.example.orderservice.dtos.OrderDTO;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO) throws BadRequestException;

    List<OrderDTO> getAllOrders();

    void update( Long id) throws BadRequestException;
}
