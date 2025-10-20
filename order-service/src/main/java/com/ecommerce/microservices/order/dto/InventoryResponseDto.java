package com.ecommerce.microservices.order.dto;

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

  @Builder.Default
  private Boolean fallback = false;

  private String message;
}
