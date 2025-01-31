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
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
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
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) throws ExternalServiceException, BadRequestException {
        Order orderFromDTO = orderMapper.dtoToEntity(orderDTO);
        orderFromDTO.setStatus(OrderStatus.PENDING);
        orderFromDTO.getProducts().forEach(orderItem -> orderItem.setOrder(orderFromDTO));
        Order savedOrder;
        try {
            if (existUserFromService(orderDTO.getUserId()) && checkStockFromService(orderDTO)) {
                savedOrder = orderRepository.save(orderFromDTO);
                rabbitTemplate.convertAndSend(rabbitValues.getExchange(), rabbitValues.getUpdateStockRoutingKey(), orderMapper.entityToDTO(savedOrder));
                return orderMapper.entityToDTO(savedOrder);
            }
        } catch (HttpStatusCodeException e) {
            log.error(e.getMessage(), e);
            throw new ExternalServiceException(e.getStatusCode(), "Could not make order: " + e.getResponseBodyAsString());
        } catch (Exception exception) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(exception.getMessage(), exception);
            throw exception;
        }
        throw new BadRequestException("Unable to create order due to invalid data");
    }


    private boolean existUserFromService(Long id) {
        return userRestTemplate.getForEntity("/existUser/{userId}", Boolean.class, id).getStatusCode() == HttpStatus.OK;
    }

    private boolean checkStockFromService(OrderDTO orderDTO) {
        Set<RequestStockValidationDTO> products = orderDTO.getProducts()
                .stream()
                .map(orderItemDTO -> new RequestStockValidationDTO(orderItemDTO.getProductId(), orderItemDTO.getQuantity()))
                .collect(Collectors.toSet());
        return productRestTemplate.postForEntity("/existStock", orderDTO.getProducts(), Boolean.class).getStatusCode() == HttpStatus.OK;
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
    @Transactional
    public void update(Long id) {
        Order orderFound = orderRepository
                .findById(id)
                .orElseThrow();
        orderFound.setStatus(OrderStatus.COMPLETED);
        try {
            Order orderSaved = orderRepository.save(orderFound);
            rabbitTemplate.convertAndSend(rabbitValues.getExchange(), rabbitValues.getCreatedOrderRoutingKey(), orderMapper.entityToDTO(orderSaved));
        } catch (Exception exception) {
//            TODO: realizar el restock en caso de que no se pueda realizar actualización.
//            rabbitTemplate.convertAndSend(rabbitValues.getExchange(), rabbitValues.getUpdateStockRoutingKey(), orderMapper.entityToDTO(orderFound));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }
}
