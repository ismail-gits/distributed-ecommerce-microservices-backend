package com.ecommerce.microservices.notification.exception;

public class NotificationNotSentException extends RuntimeException {
  public NotificationNotSentException(String message) {
    super(message);
  }
}
