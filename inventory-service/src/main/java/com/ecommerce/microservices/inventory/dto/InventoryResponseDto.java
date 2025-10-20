package com.ecommerce.microservices.inventory.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class InventoryResponseDto {

  private String skuCode;

  private Boolean inStock;

  private Integer requestedQuantity;

  private Integer availableQuantity;

  private String message;
}
