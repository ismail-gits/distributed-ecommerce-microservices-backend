package com.ecommerce.microservices.order.controller;

import com.ecommerce.microservices.order.dto.OrderRequestDto;
import com.ecommerce.microservices.order.dto.OrderResponseDto;
import com.ecommerce.microservices.order.dto.OrderUpdateDto;
import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import com.ecommerce.microservices.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequestDto));
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  @GetMapping("/paged")
  public ResponseEntity<List<OrderResponseDto>> getAllOrdersPaged(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(orderService.getAllOrdersPaged(page, size));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.getOrderById(orderId));
  }

  @GetMapping("/status/{orderStatus}")
  public ResponseEntity<List<OrderResponseDto>> getAllOrdersByStatus(@PathVariable OrderStatusType orderStatus) {
    return ResponseEntity.ok(orderService.getAllOrdersByStatus(orderStatus));
  }

  @PatchMapping("/{orderId}")
  public ResponseEntity<OrderResponseDto> updateOrderById(
      @PathVariable Long orderId,
      @RequestBody OrderUpdateDto orderUpdateDto
  ) {
    return ResponseEntity.ok(orderService.updateOrderById(orderId, orderUpdateDto));
  }

  @PatchMapping("/{orderId}/cancel")
  public ResponseEntity<OrderResponseDto> cancelOrderById(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.cancelOrderById(orderId));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId) {
    orderService.deleteOrderById(orderId);
    return ResponseEntity.noContent().build();
  }
}
