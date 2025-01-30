package com.example.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiClientConfig {
    @Value("${user.service.url}")
    private String userServiceUrl;
    @Value("${product.service.url}")
    private String productServiceUrl;

    @Bean
    public RestTemplate userRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(userServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestTemplate productRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(productServiceUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
