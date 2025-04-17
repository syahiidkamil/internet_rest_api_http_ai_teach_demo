package com.syahiidkamil.internet_rest_http_ai_demo.model.dto.request;

import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, BigDecimal price, Integer stock, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ProductRequestBuilder builder() {
        return new ProductRequestBuilder();
    }

    public static class ProductRequestBuilder {
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;

        ProductRequestBuilder() {
        }

        public ProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductRequestBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductRequestBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public ProductRequestBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductRequest build() {
            return new ProductRequest(name, description, price, stock, imageUrl);
        }
    }
}
