package com.ecommerce.microservices.order.entity;

import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false, unique = true)
  private String orderNumber;

  @Column(nullable = false)
  private String skuCode;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private Integer quantity;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatusType orderStatus;

  @Column(nullable = false)
  private String email;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;
}
