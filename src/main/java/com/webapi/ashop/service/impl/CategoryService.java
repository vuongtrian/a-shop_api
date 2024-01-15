package com.webapi.ashop.service.impl;

import com.webapi.ashop.entity.Category;
import com.webapi.ashop.entity.dto.CategoryRequestDTO;
import com.webapi.ashop.entity.dto.CategoryResponseDTO;
import com.webapi.ashop.exception.CategoryNotFoundException;
import com.webapi.ashop.exception.CategoryServiceException;
import com.webapi.ashop.repository.ICategoryRepository;
import com.webapi.ashop.service.ICategoryService;
import com.webapi.ashop.util.CategoryValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    @CacheEvict(value = "category")
    public CategoryResponseDTO createNewCategory(CategoryRequestDTO categoryRequestDTO) throws CategoryServiceException {
        CategoryResponseDTO categoryResponseDTO;

        try {
            log.info("CategoryService:createNewCategory execution started.");
            Category category = CategoryValueMapper.convertToEntity(categoryRequestDTO);
            log.debug("CategoryService:createNewCategory request parameters {}", CategoryValueMapper.jsonAsString(categoryRequestDTO));

            Category categoryResults = categoryRepository.save(category);
            categoryResponseDTO = CategoryValueMapper.convertToDTO(categoryResults);
            log.debug("CategoryService:createNewCategory received response from Database {}", CategoryValueMapper.jsonAsString(categoryResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting category to database , Exception message {}", ex.getMessage());
            throw new CategoryServiceException("Exception occurred while create a new category");
        }
        log.info("CategoryService:createNewCategory execution ended.");
        return categoryResponseDTO;
    }

    @Override

    @Caching(put = {
            @CachePut(value = "category", key = "#categoryId")
    }, evict = {
            @CacheEvict(value = "category", key = "'allCategories'")
    })
    public CategoryResponseDTO updateCategory(int categoryId, CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO;

        try {
            log.info("CategoryService:updateCategory execution started.");

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + categoryId));
            category.setName(categoryRequestDTO.getName());
            category.setDescription(categoryRequestDTO.getDescription());
            log.debug("CategoryService:updateCategory request parameters {}", CategoryValueMapper.jsonAsString(categoryRequestDTO));

            Category categoryResults = categoryRepository.save(category);
            categoryResponseDTO = CategoryValueMapper.convertToDTO(categoryResults);
            log.debug("CategoryService:updateCategory received response from Database {}", CategoryValueMapper.jsonAsString(categoryResponseDTO));
        }catch (Exception ex) {
            log.error("Exception occurred while update categories from database , Exception message {}", ex.getMessage());
            throw new CategoryServiceException("Exception occurred while update category");
        }
        log.info("CategoryService:updateCategory execution ended.");
        return categoryResponseDTO;
    }

    @Override
    @Cacheable(value = "category", key = "'allCategories'")
    public List<CategoryResponseDTO> getCategories() throws CategoryServiceException{
        List<CategoryResponseDTO> categoryResponseDTOS = null;
        try {
            log.info("CategoryService:getCategories execution started.");

            List<Category> categoryList = categoryRepository.findAll();

            if (!categoryList.isEmpty()) {
                categoryResponseDTOS = categoryList.stream()
                        .map(CategoryValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                categoryResponseDTOS = Collections.emptyList();
            }

            log.debug("CategoryService:getCategories retrieving category from database  {}", CategoryValueMapper.jsonAsString(categoryResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving categories from database , Exception message {}", ex.getMessage());
            throw new CategoryServiceException("Exception occurred while fetch all categories from Database");
        }

        log.info("CategoryService:getCategories execution ended.");
        return categoryResponseDTOS;
    }

    @Override
    @Cacheable(value = "category", key = "#categoryId")
    public CategoryResponseDTO getCategoryById(int categoryId) {
        CategoryResponseDTO categoryResponseDTO;

        try {
            log.info("CategoryService:getCategoryById execution started.");

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + categoryId));
            categoryResponseDTO = CategoryValueMapper.convertToDTO(category);

            log.debug("CategoryService:getCategoryById retrieving category from database for id {} {}", categoryId, CategoryValueMapper.jsonAsString(categoryResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving category {} from database , Exception message {}", categoryId, ex.getMessage());
            throw new CategoryServiceException("Exception occurred while fetch category from Database " + categoryId);
        }

        log.info("CategoryService:getCategoryById execution ended.");
        return categoryResponseDTO;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#categoryId"),
            @CacheEvict(value = "category", key = "'allCategories'")
    })
    public void deleteCategoryById(int categoryId) {
        try {
            log.info("CategoryService:deleteCategoryById execution started.");
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + categoryId));

            log.debug("CategoryService:deleteCategoryById deleting category from database for id {} {}", categoryId, CategoryValueMapper.jsonAsString(category));
            categoryRepository.deleteById(categoryId);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting category {} from database , Exception message {}", categoryId, ex.getMessage());
            throw new CategoryServiceException("Exception occurred while delete category from Database " + categoryId);
        }
        log.info("CategoryService:deleteCategoryById execution ended.");
    }
}
