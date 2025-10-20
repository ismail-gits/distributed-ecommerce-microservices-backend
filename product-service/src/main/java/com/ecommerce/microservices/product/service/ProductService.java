package com.ecommerce.microservices.product.service;

import com.ecommerce.microservices.product.dto.ProductRequestDto;
import com.ecommerce.microservices.product.dto.ProductResponseDto;
import com.ecommerce.microservices.product.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {
  ProductResponseDto createProduct(ProductRequestDto productRequestDto);

  List<ProductResponseDto> getAllProducts();

  List<ProductResponseDto> getAllProductsPaged(int page, int size);

  ProductResponseDto getProductById(String productId);

  void deleteProductById(String productId);

  ProductResponseDto updateProductById(String id, ProductRequestDto productRequestDto);

  ProductResponseDto updateProductByIdPartial(String productId, ProductUpdateDto productUpdateDto);
}
