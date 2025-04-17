package com.syahiidkamil.internet_rest_http_ai_demo.service.impl;

import com.syahiidkamil.internet_rest_http_ai_demo.exception.CustomException;
import com.syahiidkamil.internet_rest_http_ai_demo.exception.ErrorCode;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.ProductRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.ProductResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.Product;
import com.syahiidkamil.internet_rest_http_ai_demo.repository.ProductRepository;
import com.syahiidkamil.internet_rest_http_ai_demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .imageUrl(productRequest.getImageUrl())
                .build();

        Product savedProduct = productRepository.save(product);
        return toProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStock(productRequest.getStock());
        existingProduct.setImageUrl(productRequest.getImageUrl());

        Product updatedProduct = productRepository.save(existingProduct);
        return toProductResponse(updatedProduct);
    }

    @Override
    public ProductResponse patchProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // Only update non-null fields
        if (productRequest.getName() != null) {
            existingProduct.setName(productRequest.getName());
        }
        if (productRequest.getDescription() != null) {
            existingProduct.setDescription(productRequest.getDescription());
        }
        if (productRequest.getPrice() != null) {
            existingProduct.setPrice(productRequest.getPrice());
        }
        if (productRequest.getStock() != null) {
            existingProduct.setStock(productRequest.getStock());
        }
        if (productRequest.getImageUrl() != null) {
            existingProduct.setImageUrl(productRequest.getImageUrl());
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return toProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
