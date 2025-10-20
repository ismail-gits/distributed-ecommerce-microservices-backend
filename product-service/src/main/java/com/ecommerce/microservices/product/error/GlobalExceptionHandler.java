package com.ecommerce.microservices.product.error;

import com.ecommerce.microservices.product.exception.ProductNotFoundException;
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

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ApiError> handleProductNotFoundException(ProductNotFoundException ex) {
    ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.put(error.getField(), error.getDefaultMessage());
    }

    ApiError apiError = new ApiError("Validation Failed", HttpStatus.BAD_REQUEST, fieldErrors);
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }
}
