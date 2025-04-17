package com.syahiidkamil.internet_rest_http_ai_demo.controller;

import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request.ProductRequest;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.ProductResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.model.dto.response.WebResponse;
import com.syahiidkamil.internet_rest_http_ai_demo.service.ProductService;
import com.syahiidkamil.internet_rest_http_ai_demo.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ApiResponse apiResponse;

    public ProductController(ProductService productService, ApiResponse apiResponse) {
        this.productService = productService;
        this.apiResponse = apiResponse;
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(
                apiResponse.success(products)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(
                apiResponse.success(product)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<WebResponse<List<ProductResponse>>> searchProducts(@RequestParam String name) {
        List<ProductResponse> products = productService.getProductsByName(name);
        return ResponseEntity.ok(
                apiResponse.success(products)
        );
    }

    @PostMapping
    public ResponseEntity<WebResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        ProductResponse createdProduct = productService.createProduct(request);
        return new ResponseEntity<>(
                apiResponse.created(createdProduct),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        ProductResponse updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(
                apiResponse.success(updatedProduct, "Product updated successfully")
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WebResponse<ProductResponse>> patchProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        ProductResponse patchedProduct = productService.patchProduct(id, request);
        return ResponseEntity.ok(
                apiResponse.success(patchedProduct, "Product partially updated successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
                apiResponse.success("Product deleted successfully")
        );
    }
}
