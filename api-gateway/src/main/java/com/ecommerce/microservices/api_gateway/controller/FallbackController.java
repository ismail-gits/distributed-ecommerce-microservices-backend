package com.ecommerce.microservices.api_gateway.controller;

import com.ecommerce.microservices.api_gateway.util.FallbackResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fallback")
public class FallbackController {

  private final FallbackResponseBuilder fallbackResponseBuilder;

  @GetMapping("/product-service")
  @PostMapping("/product-service")
  @PutMapping("/product-service")
  @PatchMapping("/product-service")
  @DeleteMapping("/product-service")
  public ResponseEntity<Map<String, Object>> productServiceFallback() {
    return fallbackResponseBuilder.buildFallbackResponse(
        "Product Service",
        "Product Service is currently unavailable. Please try again later."
    );
  }

  @GetMapping("/order-service")
  @PostMapping("/order-service")
  @PutMapping("/order-service")
  @PatchMapping("/order-service")
  @DeleteMapping("/order-service")
  public ResponseEntity<Map<String, Object>> orderServiceFallback() {
    return fallbackResponseBuilder.buildFallbackResponse(
        "Order Service",
        "Order Service is currently unavailable. Please try again later."
    );
  }

  @GetMapping("/inventory-service")
  public ResponseEntity<Map<String, Object>> inventoryServiceFallback() {
    return fallbackResponseBuilder.buildFallbackResponse(
        "Inventory Service",
        "Inventory Service is currently unavailable. Please try again later."
    );
  }
}
