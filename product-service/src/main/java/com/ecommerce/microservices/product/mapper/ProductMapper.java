package com.ecommerce.microservices.product.mapper;

import com.ecommerce.microservices.product.dto.ProductRequestDto;
import com.ecommerce.microservices.product.dto.ProductResponseDto;
import com.ecommerce.microservices.product.document.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  // Maps Product document -> ProductResponseDto
  ProductResponseDto toResponseDto(Product product);

  // Maps ProductResponseDto -> Product document
  Product fromResponseToDocument(ProductResponseDto productResponseDto);

  // Maps ProductRequestDto -> Product document
  Product fromRequestToDocument(ProductRequestDto productRequestDto);
}
