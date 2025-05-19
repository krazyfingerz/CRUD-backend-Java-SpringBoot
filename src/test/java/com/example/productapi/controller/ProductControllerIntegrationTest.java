// Integration test to mock HTTP reqs against ProductController.java
package com.example.productapi.controller;

import com.example.productapi.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRetrieveUpdateDelete() throws Exception {
        // Create product
        ProductDTO dto = ProductDTO.builder()
                .name("Product1")
                .description("Desc")
                .price(5.0)
                .available(true)
                .build();
        String json = objectMapper.writeValueAsString(dto);

        // POST /api/products
        String response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name", is("Product1")))
            .andReturn().getResponse().getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);
        UUID id = created.getId();

        // GET /api/products/{id}
        mockMvc.perform(get("/api/products/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Product1")));

        // PUT /api/products/{id}
        created.setName("UpdatedName");
        String updateJson = objectMapper.writeValueAsString(created);
        mockMvc.perform(put("/api/products/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("UpdatedName")));

        // DELETE /api/products/{id}
        mockMvc.perform(delete("/api/products/" + id))
            .andExpect(status().isNoContent());

        // Ensure deleted (GET should 404)
        mockMvc.perform(get("/api/products/" + id))
            .andExpect(status().isNotFound());
    }

    @Test
    void testValidation() throws Exception {
        // Name blank and price negative -> should fail validation
        ProductDTO invalid = ProductDTO.builder()
                .name("") // invalid
                .description("Desc")
                .price(-1.0) // invalid
                .available(true)
                .build();
        String json = objectMapper.writeValueAsString(invalid);
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }
}
