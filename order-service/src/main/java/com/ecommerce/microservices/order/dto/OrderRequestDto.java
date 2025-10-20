package com.ecommerce.microservices.order.dto;

import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

  @NotBlank(message = "skuCode is required")
  private String skuCode;

  @NotNull(message = "price is required")
  private BigDecimal price;

  @NotNull(message = "quantity is required")
  @Min(1)
  private Integer quantity;

  @NotNull(message = "orderStatus is required")
  private OrderStatusType orderStatus;

  @NotNull(message = "Email is required")
  private String email;
}
