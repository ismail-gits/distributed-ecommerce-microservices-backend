package com.ecommerce.microservices.order.service;

import com.ecommerce.microservices.order.dto.OrderRequestDto;
import com.ecommerce.microservices.order.dto.OrderResponseDto;
import com.ecommerce.microservices.order.dto.OrderUpdateDto;
import com.ecommerce.microservices.order.entity.type.OrderStatusType;

import java.util.List;

public interface OrderService {
  OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

  List<OrderResponseDto> getAllOrders();

  List<OrderResponseDto> getAllOrdersPaged(int page, int size);

  OrderResponseDto getOrderById(Long orderId);

  OrderResponseDto updateOrderById(Long orderId, OrderUpdateDto orderUpdateDto);

  OrderResponseDto cancelOrderById(Long orderId);

  List<OrderResponseDto> getAllOrdersByStatus(OrderStatusType orderStatus);

  void deleteOrderById(Long orderId);
}
