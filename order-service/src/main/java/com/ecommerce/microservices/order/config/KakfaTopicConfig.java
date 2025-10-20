package com.ecommerce.microservices.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakfaTopicConfig {

  @Value("${kafka.topic.order-placed-topic}")
  private String KAFKA_ORDER_PLACED_TOPIC;

  @Bean
  public NewTopic orderPlacedTopic() {
    // Create a topic with 3 partitions and replication factor of 1
    return new NewTopic(KAFKA_ORDER_PLACED_TOPIC, 3, (short) 1);
  }
}
