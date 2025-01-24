package com.example.orderservice.services;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.exceptions.ExternalServiceException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO) throws BadRequestException, ExternalServiceException;

    List<OrderDTO> getAllOrders();

    void update( Long id) throws BadRequestException;
}
