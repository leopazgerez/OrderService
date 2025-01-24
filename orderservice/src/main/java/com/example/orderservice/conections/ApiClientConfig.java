package com.example.orderservice.conections;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiClientConfig {
    @Bean
    public RestTemplate userRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8080/users")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public RestTemplate productRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri("http://localhost:8081/products")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
