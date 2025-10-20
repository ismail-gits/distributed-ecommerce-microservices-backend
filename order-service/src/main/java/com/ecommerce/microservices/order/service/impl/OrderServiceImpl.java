package com.ecommerce.microservices.order.service.impl;

import com.ecommerce.microservices.order.client.InventoryClient;
import com.ecommerce.microservices.order.dto.InventoryResponseDto;
import com.ecommerce.microservices.order.dto.OrderRequestDto;
import com.ecommerce.microservices.order.dto.OrderResponseDto;
import com.ecommerce.microservices.order.dto.OrderUpdateDto;
import com.ecommerce.microservices.order.entity.Order;
import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import com.ecommerce.microservices.order.exception.InsufficientStockException;
import com.ecommerce.microservices.order.exception.InventoryServiceNotAvailableException;
import com.ecommerce.microservices.order.exception.OrderNotFoundException;
import com.ecommerce.microservices.order.kafka.OrderPlacedEventProducer;
import com.ecommerce.microservices.order.mapper.OrderMapper;
import com.ecommerce.microservices.order.repository.OrderRepository;
import com.ecommerce.microservices.order.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  // Injecting Feign client
  private final InventoryClient inventoryClient;

  private final OrderPlacedEventProducer orderPlacedEventProducer;

  @Override
  public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
    // Check stock with inventory service
    InventoryResponseDto inventoryResponseDto = inventoryClient.checkStock(orderRequestDto.getSkuCode(), orderRequestDto.getQuantity());

    if (inventoryResponseDto.getFallback() != null && inventoryResponseDto.getFallback()) {
      log.warn("Inventory service unavailable, using fallback for SKU: {}", inventoryResponseDto.getSkuCode());
      throw new InventoryServiceNotAvailableException("Inventory Service is currently unavailable. Please try again later.");
    } else if (!inventoryResponseDto.getInStock()) {
      log.info(
          "Insufficient stock for SKU: {}, Requested Quantity: {}, Available Quantity: {}",
          inventoryResponseDto.getSkuCode(),
          inventoryResponseDto.getRequestedQuantity(),
          inventoryResponseDto.getAvailableQuantity()
      );

      throw new InsufficientStockException(
          inventoryResponseDto.getSkuCode(),
          inventoryResponseDto.getRequestedQuantity(),
          inventoryResponseDto.getAvailableQuantity()
      );
    }

    Order order = orderMapper.fromRequestToDto(orderRequestDto);
    order.setOrderNumber(UUID.randomUUID().toString());

    order = orderRepository.save(order);

    log.info("Order created successfully: id: {}, orderNumber: {}", order.getId(), order.getOrderNumber());

    orderPlacedEventProducer.sendOrderPlacedEvent(order);

    return orderMapper.toResponseDto(order);
  }

  @Override
  public List<OrderResponseDto> getAllOrders() {
    return orderRepository.findAll()
        .stream()
        .map(orderMapper::toResponseDto)
        .toList();
  }

  @Override
  public List<OrderResponseDto> getAllOrdersPaged(int page, int size) {
    return orderRepository.findAll(PageRequest.of(page, size))
        .stream()
        .map(orderMapper::toResponseDto)
        .toList();
  }

  @Override
  public OrderResponseDto getOrderById(Long orderId) {
    Order order = orderRepository
        .findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    log.info("Order fetched successfully with id: {}", order.getId());

    return orderMapper.toResponseDto(order);
  }

  @Override
  @Transactional
  public OrderResponseDto updateOrderById(Long orderId, OrderUpdateDto orderUpdateDto) {
    Order order = orderRepository
        .findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    if (orderUpdateDto.getOrderStatus() != null) {
      order.setOrderStatus(orderUpdateDto.getOrderStatus());
    }

    if (orderUpdateDto.getQuantity() != null) {
      order.setQuantity(orderUpdateDto.getQuantity());
    }

    log.info("Order updated successfully with id: {}", order.getId());

    return orderMapper.toResponseDto(order);
  }

  @Override
  @Transactional
  public OrderResponseDto cancelOrderById(Long orderId) {
    Order order = orderRepository
        .findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    if (order.getOrderStatus() == OrderStatusType.CANCELLED) {
      log.info("Order already cancelled with id: {}", orderId);
      return orderMapper.toResponseDto(order);
    }

    order.setOrderStatus(OrderStatusType.CANCELLED);

    log.info("Order cancelled successfully with id: {}", order.getId());

    return orderMapper.toResponseDto(order);
  }

  @Override
  public List<OrderResponseDto> getAllOrdersByStatus(OrderStatusType orderStatus) {
    return orderRepository.findAllByOrderStatus(orderStatus)
        .stream()
        .map(orderMapper::toResponseDto)
        .toList();
  }

  @Override
  @Transactional
  public void deleteOrderById(Long orderId) {
    Order order = orderRepository
        .findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    orderRepository.delete(order);

    log.info("Order deleted successfully with id: {}", order.getId());
  }
}
