package com.example.productapi.repository;

import com.example.productapi.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private final Map<UUID, Product> productStore = new ConcurrentHashMap<>();

    public Product save(Product product) {
        productStore.put(product.getId(), product);
        return product;
    }

    public Product findById(UUID id) {
        return productStore.get(id);
    }

    public Map<UUID, Product> findAll() {
        return productStore;
    }

    public void delete(UUID id) {
        productStore.remove(id);
    }
}
