package com.syahiidkamil.internet_rest_http_ai_demo.repository;

import com.syahiidkamil.internet_rest_http_ai_demo.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPriceLessThanEqual(BigDecimal price);
    List<Product> findByStockGreaterThan(Integer stock);
}
