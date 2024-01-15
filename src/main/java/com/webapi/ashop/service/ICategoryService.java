package com.webapi.ashop.service;


import com.webapi.ashop.entity.Category;
import com.webapi.ashop.entity.dto.CategoryRequestDTO;
import com.webapi.ashop.entity.dto.CategoryResponseDTO;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    CategoryResponseDTO createNewCategory(CategoryRequestDTO categoryRequestDTO);
    List<CategoryResponseDTO> getCategories();
    CategoryResponseDTO getCategoryById(int categoryId);
    CategoryResponseDTO updateCategory(int categoryId, CategoryRequestDTO categoryRequestDTO);
    void deleteCategoryById(int categoryId);


}
