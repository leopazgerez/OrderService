package com.example.orderservice.config;

import com.example.orderservice.Utils.RabbitValues;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Autowired
    private RabbitValues rabbitValues;

    /**
     * Cola de actulización de orden
     */
    @Bean
    Queue orderQueue() {
        return new Queue(rabbitValues.getUpdateOrderQueue(),
                false);
    }

    /**
     * Cola de actualizacion de stock
     */
    @Bean
    Queue stockQueue() {
        return new Queue(rabbitValues.getUpdateStockQueue(), false);
    }

    /**
     * Buzón
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(rabbitValues.getExchange());
    }

    /**
     * Crea la union o conexión del mje de la cola de orden
     */
    @Bean
    Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(rabbitValues.getUpdateOrderRoutingKey());
    }

    /**
     * Crea la union o conexión del mje de la cola de stock
     */
    @Bean
    Binding stockBinding() {
        return BindingBuilder
                .bind(stockQueue())
                .to(exchange())
                .with(rabbitValues.getUpdateStockRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * Configuracion del template
     */
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
