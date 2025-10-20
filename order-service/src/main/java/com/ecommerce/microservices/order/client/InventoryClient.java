package com.ecommerce.microservices.order.client;

import com.ecommerce.microservices.order.dto.InventoryResponseDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url = "${inventory.service.url}")
public interface InventoryClient {

  Logger log = LoggerFactory.getLogger(InventoryClient.class);

  @GetMapping("/api/v1/inventory")
  @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackCheckStock")
  @Retry(name = "inventory")
  InventoryResponseDto checkStock(
    @RequestParam String skuCode,
    @RequestParam Integer quantity
  );

  default InventoryResponseDto fallbackCheckStock(String skuCode, Integer quantity, Exception ex) {
    log.info("Cannot get inventory for skuCode: {}. failure reason: {}", skuCode, ex.getMessage());

    return InventoryResponseDto.builder()
        .skuCode(skuCode)
        .inStock(false)
        .requestedQuantity(quantity)
        .availableQuantity(null)
        .fallback(true)
        .message(ex.getMessage())
        .build();
  }
}
