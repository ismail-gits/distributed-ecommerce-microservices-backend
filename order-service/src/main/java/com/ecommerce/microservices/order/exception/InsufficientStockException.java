package com.ecommerce.microservices.order.exception;

public class InsufficientStockException extends RuntimeException {
  public InsufficientStockException(String skuCode, Integer requestedQuantity, Integer availableQuantity) {
    super(String.format(
        "Insufficient stock for SKU: %s, Requested Quantity: %d, Available Quantity: %d",
        skuCode, requestedQuantity, availableQuantity
    ));
  }
}
