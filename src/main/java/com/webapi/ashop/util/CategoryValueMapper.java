package com.webapi.ashop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapi.ashop.entity.Category;
import com.webapi.ashop.entity.dto.CategoryRequestDTO;
import com.webapi.ashop.entity.dto.CategoryResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryValueMapper {
    public static Category convertToEntity(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        return category;
    }

    public static CategoryResponseDTO convertToDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setDescription(category.getDescription());
        return categoryResponseDTO;
    }

    public static List<CategoryResponseDTO> convertToListDTO (List<Category> categories) {
        List<CategoryResponseDTO> categoryResponseDTOS = categories.stream().map(category -> convertToDTO(category)).collect(Collectors.toList());
        return categoryResponseDTOS;
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
