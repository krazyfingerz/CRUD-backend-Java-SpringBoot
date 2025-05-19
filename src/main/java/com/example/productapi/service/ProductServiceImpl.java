// Business logic implemented here
package com.example.productapi.service;

import com.example.productapi.dto.ProductDTO;
import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.mapper.ProductMapper;
import com.example.productapi.model.Product;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapper.toEntity(productDTO);
        product.setId(UUID.randomUUID());
        // default availability if not provided
        if (product.getAvailable() == null) {
            product.setAvailable(false);
        }
        repository.save(product);
        return mapper.toDTO(product);
    }

    @Override
    public ProductDTO getProductById(UUID id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
        return mapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repository.findAll().stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Product existing = repository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
        existing.setName(productDTO.getName());
        existing.setDescription(productDTO.getDescription());
        existing.setPrice(productDTO.getPrice());
        existing.setAvailable(productDTO.getAvailable());
        repository.save(existing);
        return mapper.toDTO(existing);
    }

    @Override
    public void deleteProduct(UUID id) {
        if (!repository.findById(id).isPresent()) {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
        repository.deleteById(id);
    }
}
