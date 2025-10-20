package com.ecommerce.microservices.notification.error;

import com.ecommerce.microservices.notification.exception.NotificationNotSentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotificationNotSentException.class)
  public ResponseEntity<ApiError> handleOrderNotFoundException(NotificationNotSentException ex) {
    ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
