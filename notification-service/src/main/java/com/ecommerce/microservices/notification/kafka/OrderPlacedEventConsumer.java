package com.ecommerce.microservices.notification.kafka;

import com.ecommerce.microservices.notification.exception.NotificationNotSentException;
import com.ecommerce.microservices.order.event.OrderPlacedEvent;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedEventConsumer {

  private final JavaMailSender javaMailSender;

  @KafkaListener(topics = "order-placed-topic")
  public void handleOrderPlacedTopic(OrderPlacedEvent orderPlacedEvent) {
    log.info("Message from topic: order-placed-topic, message: {}", orderPlacedEvent);

    MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      mimeMessageHelper.setFrom("order@ismaildevzone.tech");
      mimeMessageHelper.setTo(orderPlacedEvent.getEmail().toString());
      mimeMessageHelper.setSubject("Your Order with OrderNumber: %s is placed successfully".formatted(orderPlacedEvent.getOrderNumber()));
      mimeMessageHelper.setText("""
            Hi
            
            Your order with order number %s is now placed successfully.
            
            Best Regards
            Ecommerce 
         """.formatted(orderPlacedEvent.getOrderNumber())
      );
    };

    try {
      javaMailSender.send(mimeMessagePreparator);
      log.info("Order notification email sent!");
    } catch (MailException ex) {
      log.error("Exception occurred when sending email: {}", String.valueOf(ex));
      throw new NotificationNotSentException("Exception occurred when sending mail to %s: %s".formatted(orderPlacedEvent.getEmail(), ex.getMessage()));
    }
  }
}
