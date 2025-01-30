package com.example.orderservice.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitValues {
    @Value("${rabbitmq.queue.order.name}")
    private String updateOrderQueue;

    @Value("${rabbitmq.queue.stock.name}")
    private String updateStockQueue;

    @Value("${rabbitmq.queue.created.order.name}")
    private String createdOrderQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.order.key}")
    private String updateOrderRoutingKey;

    @Value("${rabbitmq.routing.stock.key}")
    private String updateStockRoutingKey;

    @Value("${rabbitmq.routing.created.order.key}")
    private String createdOrderRoutingKey;

    public String getUpdateOrderQueue() {
        return updateOrderQueue;
    }

    public String getUpdateStockQueue() {
        return updateStockQueue;
    }

    public String getExchange() {
        return exchange;
    }

    public String getUpdateOrderRoutingKey() {
        return updateOrderRoutingKey;
    }

    public String getUpdateStockRoutingKey() {
        return updateStockRoutingKey;
    }

    public String getCreatedOrderQueue() {
        return createdOrderQueue;
    }

    public String getCreatedOrderRoutingKey() {
        return createdOrderRoutingKey;
    }
}
