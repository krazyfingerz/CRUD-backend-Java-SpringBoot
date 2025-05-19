package com.example.productapi.dto;

import lombok.*;
import java.util.UUID;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be >= 0")
    private Double price;

    private Boolean available;
}
