package com.syahiidkamil.internet_rest_http_ai_demo.service;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.ProductRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsByName(String name);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    ProductResponse patchProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}
