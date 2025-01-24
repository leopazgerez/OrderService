package com.example.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ExternalServiceException extends Exception {
    private final HttpStatusCode status;

    public ExternalServiceException(HttpStatusCode status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
