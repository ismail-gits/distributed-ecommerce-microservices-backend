package com.ecommerce.microservices.notification.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ApiError {

  private LocalDateTime timeStamp;

  private String error;

  private HttpStatus statusCode;

  private Map<String, String> fieldErrors;

  public ApiError() {
    this.timeStamp = LocalDateTime.now();
  }

  public ApiError(String error, HttpStatus statusCode) {
    this();
    this.error = error;
    this.statusCode = statusCode;
  }

  public ApiError(String error, HttpStatus statusCode, Map<String, String> fieldErrors) {
    this(error, statusCode);
    this.fieldErrors = fieldErrors;
  }
}
