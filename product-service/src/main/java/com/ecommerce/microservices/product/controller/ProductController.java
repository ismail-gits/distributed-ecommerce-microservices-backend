package com.ecommerce.microservices.product.controller;

import com.ecommerce.microservices.product.dto.ProductRequestDto;
import com.ecommerce.microservices.product.dto.ProductResponseDto;
import com.ecommerce.microservices.product.dto.ProductUpdateDto;
import com.ecommerce.microservices.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequestDto));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
    // Testing Timeout for Circuit Breaker Pattern implemented using Resilience4j
//    try {
//      Thread.sleep(5 * 1000);
//    } catch (InterruptedException ex) {
//      throw new RuntimeException(ex);
//    }
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/paged")
  public ResponseEntity<List<ProductResponseDto>> getAllProductsPaged(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(productService.getAllProductsPaged(page, size));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String productId) {
    return ResponseEntity.ok(productService.getProductById(productId));
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> updateProductById(
    @PathVariable String productId,
    @Valid @RequestBody ProductRequestDto productRequestDto
  ) {
    return ResponseEntity.ok(productService.updateProductById(productId, productRequestDto));
  }

  @PatchMapping("/{productId}")
  public ResponseEntity<ProductResponseDto> updateProductByIdPartial(
      @PathVariable String productId,
      @RequestBody ProductUpdateDto productUpdateDto
  ) {
    return ResponseEntity.ok(productService.updateProductByIdPartial(productId, productUpdateDto));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteProductById(@PathVariable String productId) {
    productService.deleteProductById(productId);
    return ResponseEntity.noContent().build();
  }
}
