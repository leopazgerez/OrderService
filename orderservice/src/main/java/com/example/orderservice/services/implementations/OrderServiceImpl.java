package com.example.orderservice.services.implementations;

import com.example.orderservice.Utils.RabbitValues;
import com.example.orderservice.dtos.OrderDTO;
import com.example.orderservice.dtos.RequestStockValidationDTO;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.exceptions.ExternalServiceException;
import com.example.orderservice.mappers.OrderMapper;
import com.example.orderservice.models.Order;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.services.OrderService;
import org.apache.coyote.BadRequestException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate productRestTemplate;
    @Autowired
    private RestTemplate userRestTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitValues rabbitValues;

    @Override
    public OrderDTO create(OrderDTO orderDTO) throws ExternalServiceException, BadRequestException {
        Order order = orderMapper.dtoToEntity(orderDTO);
        order.setStatus(OrderStatus.PENDING);
        order.getProducts().forEach(orderItem -> orderItem.setOrder(order));
        new Order();
        Order savedOrder;
        try {
            if (existUser(order.getUserId()) && checkStock(orderDTO)) {
                savedOrder = orderRepository.save(order);
                rabbitTemplate.convertAndSend(rabbitValues.getExchange(), rabbitValues.getUpdateStockRoutingKey(), orderMapper.entityToDTO(savedOrder));
                return orderMapper.entityToDTO(savedOrder);
            }
        } catch (HttpStatusCodeException e) {
            throw new ExternalServiceException(e.getStatusCode(), "Could not make order: " + e.getResponseBodyAsString());
        }
        throw new BadRequestException("Unable to create order due to invalid data");
    }

    private boolean existUser(Long id) {
        return userRestTemplate.getForEntity("/existUser/{userId}", Boolean.class, id).getStatusCode() == HttpStatus.OK;
    }

    private boolean checkStock(OrderDTO orderDTO) {
        Set<RequestStockValidationDTO> products = orderDTO.getProducts()
                .stream()
                .map(orderItemDTO -> new RequestStockValidationDTO(orderItemDTO.getProductId(), orderItemDTO.getQuantity()))
                .collect(Collectors.toSet());
        return productRestTemplate.postForEntity("/existStock", orderDTO.getProducts(), Boolean.class).getStatusCode() == HttpStatus.OK;
    }

    private void reStock(OrderDTO orderDTO) {
        Set<RequestStockValidationDTO> products = orderDTO.getProducts()
                .stream()
                .map(orderItemDTO -> new RequestStockValidationDTO(orderItemDTO.getProductId(), orderItemDTO.getQuantity()))
                .collect(Collectors.toSet());
        productRestTemplate.put("/updateStock", products, String.class);
    }


    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @RabbitListener(queues = "#{rabbitValues.updateOrderQueue}")
    public void update(Long id) throws BadRequestException {
        Order orderFound = orderRepository
                .findById(id)
                .orElseThrow();
        orderFound.setStatus(OrderStatus.PENDING);
        try {
            throw new BadRequestException();
//            orderRepository.save(orderFound);
        } catch (Exception e) {
//            TODO: realizar el restock en caso de que no se pueda realizar actualización.
//            rabbitTemplate.convertAndSend(rabbitValues.getExchange(), rabbitValues.getUpdateStockRoutingKey(), orderMapper.entityToDTO(orderFound));
            System.out.println("kjañsldkfañsl" +e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
