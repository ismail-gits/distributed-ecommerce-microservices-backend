package com.ecommerce.microservices.order.repository;

import com.ecommerce.microservices.order.entity.Order;
import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByOrderStatus(OrderStatusType orderStatus);
}