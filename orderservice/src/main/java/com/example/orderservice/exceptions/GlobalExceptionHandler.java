package com.example.orderservice.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    ResponseEntity<?> handleException(Exception exception) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<?> handleExternalServiceException(ExternalServiceException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
