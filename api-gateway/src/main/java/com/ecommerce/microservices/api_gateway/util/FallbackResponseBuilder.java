package com.ecommerce.microservices.api_gateway.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class FallbackResponseBuilder {

  public ResponseEntity<Map<String, Object>> buildFallbackResponse(String service, String message) {
    Map<String, Object> response = new HashMap<>();

    response.put("timestamp", LocalDateTime.now());
    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
    response.put("error", "Service Unavailable");
    response.put("service", service);
    response.put("message", message);
    response.put("fallback", true);

    return ResponseEntity
        .status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(response);
  }
}
