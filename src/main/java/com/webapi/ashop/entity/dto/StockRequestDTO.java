package com.webapi.ashop.entity.dto;

import com.webapi.ashop.entity.FileData;
import com.webapi.ashop.entity.enumObj.ProductSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StockRequestDTO {

    @NotBlank(message = "Product color shouldn't be NULL OR EMPTY")
    @Size(min=3
            ,max = 50,
            message = "Product color must be between 3 and 50 characters")
    private String color;
    @NotBlank(message = "Product size shouldn't be NULL OR EMPTY")
    private ProductSize size;
    @Min(value = 0, message = "Product quantity must higher than 0")
    private int quantity;
    List<FileData> images;
}
