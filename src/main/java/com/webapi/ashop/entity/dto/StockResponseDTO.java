package com.webapi.ashop.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.webapi.ashop.entity.enumObj.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockResponseDTO {
    private Integer id;
    private String color;
    private ProductSize size;
    private int quantity;
    List<FileDataResponseDTO> images;
}
