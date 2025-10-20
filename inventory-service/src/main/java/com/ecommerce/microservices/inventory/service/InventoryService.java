package com.ecommerce.microservices.inventory.service;

import com.ecommerce.microservices.inventory.dto.InventoryResponseDto;
import org.springframework.stereotype.Service;

public interface InventoryService {

  InventoryResponseDto checkStock(String skuCode, Integer quantity);
}
