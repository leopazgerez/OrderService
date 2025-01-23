package com.example.orderservice.services.implementations;

import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.mappers.OrderMapper;
import com.example.orderservice.models.Order;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.services.OrderService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public OrderDTO create(OrderDTO orderDTO) throws BadRequestException {
        Order order = orderMapper.dtoToEntity(orderDTO);
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder;
        try {
            savedOrder = orderRepository.save(order);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return orderMapper.entityToDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Long id) throws BadRequestException {
        Order orderFound = orderRepository.findById(id).orElseThrow();
        if (orderFound.getStatus() == OrderStatus.COMPLETED) {
            orderFound.setStatus(OrderStatus.PENDING);
        } else {
            orderFound.setStatus(OrderStatus.COMPLETED);
        }
        try {
            orderRepository.save(orderFound);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
