package com.ecommerce.microservices.inventory.controller;

import com.ecommerce.microservices.inventory.dto.InventoryResponseDto;
import com.ecommerce.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class inventoryController {

  private final InventoryService inventoryService;

  @GetMapping
  public ResponseEntity<InventoryResponseDto> checkStock(
      @RequestParam String skuCode,
      @RequestParam Integer quantity
  ) {
    return ResponseEntity.ok(inventoryService.checkStock(skuCode, quantity));
  }
}
