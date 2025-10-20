package com.ecommerce.microservices.product.service.impl;

import com.ecommerce.microservices.product.dto.ProductRequestDto;
import com.ecommerce.microservices.product.dto.ProductResponseDto;
import com.ecommerce.microservices.product.dto.ProductUpdateDto;
import com.ecommerce.microservices.product.exception.ProductNotFoundException;
import com.ecommerce.microservices.product.mapper.ProductMapper;
import com.ecommerce.microservices.product.document.Product;
import com.ecommerce.microservices.product.respository.ProductRepository;
import com.ecommerce.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
    Product product = productMapper.fromRequestToDocument(productRequestDto);

    product = productRepository.insert(product);

    log.info("Product created successfully: id={}, name:{}", product.getId(), product.getName());

    return productMapper.toResponseDto(product);
  }

  @Override
  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll()
        .stream()
        .map(productMapper::toResponseDto)
        .toList();
  }

  @Override
  public List<ProductResponseDto> getAllProductsPaged(int page, int size) {
    return productRepository.findAll(PageRequest.of(page, size))
        .stream()
        .map(productMapper::toResponseDto)
        .toList();
  }

  @Override
  public ProductResponseDto getProductById(String productId) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

    log.info("Product fetched successfully: id={}, name:{}", product.getId(), product.getName());

    return productMapper.toResponseDto(product);
  }

  @Override
  public ProductResponseDto updateProductById(String productId, ProductRequestDto productRequestDto) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

    product.setName(productRequestDto.getName());
    product.setDescription(productRequestDto.getDescription());
    product.setPrice(productRequestDto.getPrice());

    productRepository.save(product);

    log.info("Product updated successfully: id={}, name:{}", product.getId(), product.getName());

    return productMapper.toResponseDto(product);
  }

  @Override
  public void deleteProductById(String productId) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

    productRepository.delete(product);

    log.info("Product deleted successfully: id={}, name:{}", product.getId(), product.getName());
  }

  @Override
  public ProductResponseDto updateProductByIdPartial(String productId, ProductUpdateDto productUpdateDto) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

    if (productUpdateDto.getName() != null && !productUpdateDto.getName().isBlank()) {
      product.setName(productUpdateDto.getName());
    }

    if (productUpdateDto.getDescription() != null && !productUpdateDto.getDescription().isBlank()) {
      product.setDescription(productUpdateDto.getDescription());
    }

    if (productUpdateDto.getPrice() != null) {
      product.setPrice(productUpdateDto.getPrice());
    }

    product = productRepository.save(product);

    log.info("Product partially updated successfully: id={}, name:{}", product.getId(), product.getName());

    return productMapper.toResponseDto(product);
  }
}
