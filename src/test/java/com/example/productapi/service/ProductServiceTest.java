// This uni test uses JUnit5 to test ProductServiceImpl without having to start the server
package com.example.productapi.service;

import com.example.productapi.dto.ProductDTO;
import com.example.productapi.exception.ProductNotFoundException;
import com.example.productapi.mapper.ProductMapper;
import com.example.productapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setup() {
        ProductRepository repo = new ProductRepository();
        // MapStruct mapper instance
        ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
        productService = new ProductServiceImpl(repo, mapper);
    }

    @Test
    void testCreateAndGetProduct() {
        ProductDTO dto = ProductDTO.builder()
                .name("Test Product")
                .description("Sample description")
                .price(10.0)
                .available(true)
                .build();
        ProductDTO created = productService.createProduct(dto);
        assertNotNull(created.getId());

        ProductDTO fetched = productService.getProductById(created.getId());
        assertEquals("Test Product", fetched.getName());
        assertEquals("Sample description", fetched.getDescription());
        assertEquals(10.0, fetched.getPrice());
        assertTrue(fetched.getAvailable());
    }

    @Test
    void testUpdateProduct() {
        ProductDTO dto = ProductDTO.builder()
                .name("Old Name")
                .description("Desc")
                .price(5.0)
                .available(false)
                .build();
        ProductDTO created = productService.createProduct(dto);
        created.setName("New Name");
        ProductDTO updated = productService.updateProduct(created.getId(), created);
        assertEquals("New Name", updated.getName());
    }

    @Test
    void testDeleteProduct() {
        ProductDTO dto = ProductDTO.builder()
                .name("ToDelete")
                .description("Desc")
                .price(1.0)
                .available(true)
                .build();
        ProductDTO created = productService.createProduct(dto);
        assertNotNull(productService.getProductById(created.getId()));
        productService.deleteProduct(created.getId());
        assertThrows(ProductNotFoundException.class, 
                     () -> productService.getProductById(created.getId()));
    }

    @Test
    void testGetNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, 
                     () -> productService.getProductById(UUID.randomUUID()));
    }
}
