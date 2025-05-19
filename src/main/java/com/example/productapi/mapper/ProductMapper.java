// Uses Mapstruct to convert between Product and ProductDTO
package com.example.productapi.mapper;

import org.mapstruct.Mapper;
import com.example.productapi.model.Product;
import com.example.productapi.dto.ProductDTO;

// conponentModel spring creates a spring bean from this mapper, inject wehrerver needed
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);
}
