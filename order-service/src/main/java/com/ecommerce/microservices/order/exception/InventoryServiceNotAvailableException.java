package com.ecommerce.microservices.order.exception;

public class InventoryServiceNotAvailableException extends RuntimeException {
  public InventoryServiceNotAvailableException(String message) {
    super(message);
  }
}
