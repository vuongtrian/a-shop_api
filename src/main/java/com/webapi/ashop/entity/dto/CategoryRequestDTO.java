package com.webapi.ashop.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryRequestDTO {

    @NotBlank(message = "Category name shouldn't be NULL OR EMPTY")
    private String name;

    @Size(min = 50,
            max = 200,
            message = "Category description must be between 50 and 200 characters")
    private String description;
}
