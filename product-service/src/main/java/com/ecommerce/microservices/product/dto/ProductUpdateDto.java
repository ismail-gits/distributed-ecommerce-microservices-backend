package com.ecommerce.microservices.product.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductUpdateDto {

  private String name;

  private String description;

  private BigDecimal price;
}
