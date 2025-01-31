package com.example.orderservice.exceptions;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> handleBadRequestException(BadRequestException exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    ResponseEntity<?> handleException(Exception exception) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<?> handleExternalServiceException(ExternalServiceException exception) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
