package com.ecommerce.microservices.order.dto;

import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

  private Long id;

  private String orderNumber;

  private String skuCode;

  private BigDecimal price;

  private Integer quantity;

  private OrderStatusType orderStatus;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
