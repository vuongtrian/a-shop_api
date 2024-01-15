package com.webapi.ashop.controller;

import com.webapi.ashop.entity.Category;
import com.webapi.ashop.entity.dto.APIResponse;
import com.webapi.ashop.entity.dto.CategoryRequestDTO;
import com.webapi.ashop.entity.dto.CategoryResponseDTO;
import com.webapi.ashop.service.ICategoryService;
import com.webapi.ashop.util.CategoryValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    public static final String SUCCESS = "Success";

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<APIResponse> createNewCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {

        log.info("CategoryController::createNewCategory request body {}", CategoryValueMapper.jsonAsString(categoryRequestDTO));

        CategoryResponseDTO categoryResponseDTO = categoryService.createNewCategory(categoryRequestDTO);
        //Builder Design pattern

        APIResponse<CategoryResponseDTO> responseDTO = APIResponse
                .<CategoryResponseDTO>builder()
                .status(SUCCESS)
                .results(categoryResponseDTO)
                .build();

        log.info("CategoryController::createNewCategory response {}", CategoryValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable int categoryId, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.info("CategoryController::updateCategory request body {}", CategoryValueMapper.jsonAsString(categoryRequestDTO));

        CategoryResponseDTO categoryResponseDTO = categoryService.updateCategory(categoryId, categoryRequestDTO);

        APIResponse<CategoryResponseDTO> responseDTO = APIResponse
                .<CategoryResponseDTO>builder()
                .status(SUCCESS)
                .results(categoryResponseDTO)
                .build();

        log.info("CategoryController::updateCategory response {}", CategoryValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getCategories() {

        List<CategoryResponseDTO> categories = categoryService.getCategories();
        //Builder Design pattern (to avoid complex object creation headache)
        APIResponse<List<CategoryResponseDTO>> responseDTO = APIResponse
                .<List<CategoryResponseDTO>>builder()
                .status(SUCCESS)
                .results(categories)
                .build();

        log.info("CategoryController::getCategories response {}", CategoryValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable int categoryId) {

        log.info("CategoryController::getCategory by id  {}", categoryId);

        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(categoryId);
        APIResponse<CategoryResponseDTO> responseDTO = APIResponse
                .<CategoryResponseDTO>builder()
                .status(SUCCESS)
                .results(categoryResponseDTO)
                .build();

        log.info("CategoryController::getCategory by id  {} response {}", categoryId,CategoryValueMapper
                .jsonAsString(categoryResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        log.info("CategoryController::deleteCategory by id  {}", categoryId);
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
