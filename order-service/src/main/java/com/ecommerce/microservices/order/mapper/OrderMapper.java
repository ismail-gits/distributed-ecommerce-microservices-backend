package com.ecommerce.microservices.order.mapper;

import com.ecommerce.microservices.order.dto.OrderRequestDto;
import com.ecommerce.microservices.order.dto.OrderResponseDto;
import com.ecommerce.microservices.order.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  // Maps Order entity -> OrderResponseDto
  OrderResponseDto toResponseDto(Order order);

  // Maps OrderRequestDto -> Order entity
  Order fromRequestToDto(OrderRequestDto orderRequestDto);

  // Maps OrderResponseDto -> Order entity
  Order fromResponseToDto(OrderRequestDto orderRequestDto);
}
