package com.ecommerce.microservices.inventory.service.impl;

import com.ecommerce.microservices.inventory.dto.InventoryResponseDto;
import com.ecommerce.microservices.inventory.entity.Inventory;
import com.ecommerce.microservices.inventory.repository.InventoryRepository;
import com.ecommerce.microservices.inventory.service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;

  @Override
  public InventoryResponseDto checkStock(String skuCode, Integer quantity) {
    Inventory inventory = inventoryRepository
        .findBySkuCode(skuCode)
        .orElse(null);

    if (inventory == null) {
      return InventoryResponseDto.builder()
          .skuCode(skuCode)
          .inStock(false)
          .requestedQuantity(quantity)
          .availableQuantity(0)
          .message("Product not found with skuCode: " + skuCode)
          .build();
    }

    boolean inStock = inventory.getQuantity() >= quantity;

    return InventoryResponseDto.builder()
        .skuCode(skuCode)
        .inStock(inStock)
        .requestedQuantity(quantity)
        .availableQuantity(inventory.getQuantity())
        .message(inStock ? "Stock available" : "Insufficient stock")
        .build();
  }
}
