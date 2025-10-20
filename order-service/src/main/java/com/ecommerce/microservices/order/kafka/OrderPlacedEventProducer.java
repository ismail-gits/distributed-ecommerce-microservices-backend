package com.ecommerce.microservices.order.kafka;

import com.ecommerce.microservices.order.entity.Order;
import com.ecommerce.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedEventProducer {

  @Value("${kafka.topic.order-placed-topic}")
  private String KAFKA_ORDER_PLACED_TOPIC;

  // Injecting Kafka Template
  private final KafkaTemplate<Long, OrderPlacedEvent> orderPlacedEventKafkaTemplate;

  public void sendOrderPlacedEvent(Order order) {
    // Send the message to the Kakfa orderPlaced topic
    OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
    orderPlacedEvent.setOrderNumber(order.getOrderNumber());
    orderPlacedEvent.setEmail(order.getEmail());

    log.info("Sending OrderPlacedEvent: {} to topic: {}", orderPlacedEvent, KAFKA_ORDER_PLACED_TOPIC);

    orderPlacedEventKafkaTemplate.send(KAFKA_ORDER_PLACED_TOPIC, order.getId(), orderPlacedEvent)
            .whenComplete((result, ex) -> {
              if (ex == null) {
                log.info("OrderPlacedEvent: {} send successfully to topic: {}", orderPlacedEvent, KAFKA_ORDER_PLACED_TOPIC);
              } else {
                log.error("Failed to send OrderPlacedEvent: {} to topic: {}", orderPlacedEvent, KAFKA_ORDER_PLACED_TOPIC, ex);
              }
            });
  }
}
