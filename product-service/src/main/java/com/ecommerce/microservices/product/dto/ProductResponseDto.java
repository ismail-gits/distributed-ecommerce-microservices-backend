package com.ecommerce.microservices.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

  private String id;

  private String name;

  private String description;

  private BigDecimal price;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
