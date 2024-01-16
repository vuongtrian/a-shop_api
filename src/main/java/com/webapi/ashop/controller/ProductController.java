package com.webapi.ashop.controller;

import com.webapi.ashop.entity.dto.APIResponse;
import com.webapi.ashop.entity.dto.ProductRequestDTO;
import com.webapi.ashop.entity.dto.ProductResponseDTO;
import com.webapi.ashop.service.IProductService;
import com.webapi.ashop.util.ProductValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Slf4j
public class ProductController {

    public static final String SUCCESS = "Success";

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse> createNewProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {

        log.info("ProductController::createNewProduct request body {}", ProductValueMapper.jsonAsString(productRequestDTO));

        ProductResponseDTO productResponseDTO = productService.createNewProduct(productRequestDTO);
        //Builder Design pattern

        APIResponse<ProductResponseDTO> responseDTO = APIResponse
                .<ProductResponseDTO>builder()
                .status(SUCCESS)
                .results(productResponseDTO)
                .build();

        log.info("ProductController::createNewProduct response {}", ProductValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable int productId, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        log.info("ProductController::updateProduct request body {}", ProductValueMapper.jsonAsString(productRequestDTO));

        ProductResponseDTO productResponseDTO = productService.updateProduct(productId, productRequestDTO);

        APIResponse<ProductResponseDTO> responseDTO = APIResponse
                .<ProductResponseDTO>builder()
                .status(SUCCESS)
                .results(productResponseDTO)
                .build();

        log.info("ProductController::updateProduct response {}", ProductValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProducts() {

        List<ProductResponseDTO> products = productService.getProducts();
        //Builder Design pattern (to avoid complex object creation headache)
        APIResponse<List<ProductResponseDTO>> responseDTO = APIResponse
                .<List<ProductResponseDTO>>builder()
                .status(SUCCESS)
                .results(products)
                .build();

        log.info("ProductController::getProducts response {}", ProductValueMapper.jsonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable int productId) {

        log.info("ProductController::getProduct by id  {}", productId);

        ProductResponseDTO productResponseDTO = productService.getProductById(productId);
        APIResponse<ProductResponseDTO> responseDTO = APIResponse
                .<ProductResponseDTO>builder()
                .status(SUCCESS)
                .results(productResponseDTO)
                .build();

        log.info("ProductController::getProduct by id  {} response {}", productId,ProductValueMapper
                .jsonAsString(productResponseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        log.info("ProductController::deleteProduct by id  {}", productId);
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
