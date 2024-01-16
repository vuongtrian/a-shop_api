package com.webapi.ashop.entity.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductRequestDTO {

    @NotBlank(message = "Product name shouldn't be NULL OR EMPTY")
    private String name;

    @Size(min = 50,
            max = 200,
            message = "Product description must be between 50 and 200 characters")
    private String description;

    @NotNull(message = "Product price cannot be null")
    @DecimalMin(value = "0.01", message = "Product price must be greater than or equal to 0.01")
    @Digits(integer = 5, fraction = 2, message = "Product price must have up to 5 digits in total, with up to 2 decimal places")
    private BigDecimal price;
}
