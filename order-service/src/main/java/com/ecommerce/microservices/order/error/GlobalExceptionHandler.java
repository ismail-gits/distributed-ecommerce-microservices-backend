package com.ecommerce.microservices.order.error;

import com.ecommerce.microservices.order.exception.InsufficientStockException;
import com.ecommerce.microservices.order.exception.InventoryServiceNotAvailableException;
import com.ecommerce.microservices.order.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ApiError> handleOrderNotFoundException(OrderNotFoundException ex) {
    ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentValidException(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.put(error.getField(), error.getDefaultMessage());
    }

    ApiError apiError = new ApiError("Validation Failed", HttpStatus.BAD_REQUEST, fieldErrors);
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<ApiError> handleInsufficientStockException(InsufficientStockException ex) {
    ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InventoryServiceNotAvailableException.class)
  public ResponseEntity<ApiError> handleInventoryServiceNotAvailableException(InventoryServiceNotAvailableException ex) {
    ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
  }
}
