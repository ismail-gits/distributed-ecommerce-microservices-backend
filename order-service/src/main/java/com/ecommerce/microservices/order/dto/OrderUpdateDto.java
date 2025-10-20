package com.ecommerce.microservices.order.dto;

import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderUpdateDto {

  @Min(1)
  private Integer quantity;

  private OrderStatusType orderStatus;
}
