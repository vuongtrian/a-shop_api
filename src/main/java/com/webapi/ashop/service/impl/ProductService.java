package com.webapi.ashop.service.impl;

import com.webapi.ashop.entity.Product;
import com.webapi.ashop.entity.dto.ProductRequestDTO;
import com.webapi.ashop.entity.dto.ProductResponseDTO;
import com.webapi.ashop.exception.ProductNotFoundException;
import com.webapi.ashop.exception.ProductServiceException;
import com.webapi.ashop.repository.IProductRepository;
import com.webapi.ashop.service.IProductService;
import com.webapi.ashop.util.ProductValueMapper;
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
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    @CacheEvict(value = "product", key = "'allProducts'")
    public ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO) throws ProductServiceException {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:createNewProduct execution started.");
            Product product = ProductValueMapper.convertToEntity(productRequestDTO);
            log.debug("ProductService:createNewProduct request parameters {}", ProductValueMapper.jsonAsString(productRequestDTO));

            Product productResults = productRepository.save(product);
            productResponseDTO = ProductValueMapper.convertToDTO(productResults);
            log.debug("ProductService:createNewProduct received response from Database {}", ProductValueMapper.jsonAsString(productResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting product to database , Exception message {}", ex.getMessage());
            throw new ProductServiceException("Exception occurred while create a new product");
        }
        log.info("ProductService:createNewProduct execution ended.");
        return productResponseDTO;
    }

    @Override
    @Caching(put = {
            @CachePut(value = "product", key = "#productId")
    }, evict = {
            @CacheEvict(value = "product", key = "'allProducts'")
    })
    public ProductResponseDTO updateProduct(int productId, ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:updateProduct execution started.");

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            log.debug("ProductService:updateProduct request parameters {}", ProductValueMapper.jsonAsString(productRequestDTO));

            Product productResults = productRepository.save(product);
            productResponseDTO = ProductValueMapper.convertToDTO(productResults);
            log.debug("ProductService:updateProduct received response from Database {}", ProductValueMapper.jsonAsString(productResponseDTO));
        }catch (Exception ex) {
            log.error("Exception occurred while update product from database , Exception message {}", ex.getMessage());
            throw new ProductServiceException("Exception occurred while update product");
        }
        log.info("ProductService:updateProduct execution ended.");
        return productResponseDTO;
    }

    @Override
    @Cacheable(value = "product", key = "'allProducts'")
    public List<ProductResponseDTO> getProducts() throws ProductServiceException{
        List<ProductResponseDTO> productResponseDTOS;
        try {
            log.info("ProductService:getProducts execution started.");

            List<Product> productList = productRepository.findAll();

            if (!productList.isEmpty()) {
                productResponseDTOS = productList.stream()
                        .map(ProductValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                productResponseDTOS = Collections.emptyList();
            }

            log.debug("ProductService:getProducts retrieving products from database  {}", ProductValueMapper.jsonAsString(productResponseDTOS));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving products from database , Exception message {}", ex.getMessage());
            throw new ProductServiceException("Exception occurred while fetch all products from Database");
        }

        log.info("ProductService:getProducts execution ended.");
        return productResponseDTOS;
    }

    @Override
    @Cacheable(value = "product", key = "#productId")
    public ProductResponseDTO getProductById(int productId) {
        ProductResponseDTO productResponseDTO;

        try {
            log.info("ProductService:getProductById execution started.");

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
            productResponseDTO = ProductValueMapper.convertToDTO(product);

            log.debug("ProductService:getProductById retrieving product from database for id {} {}", productId, ProductValueMapper.jsonAsString(productResponseDTO));

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceException("Exception occurred while fetch product from Database " + productId);
        }

        log.info("ProductService:getProductById execution ended.");
        return productResponseDTO;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#productId"),
            @CacheEvict(value = "product", key = "'allProducts'")
    })
    public void deleteProductById(int productId) {
        try {
            log.info("ProductService:deleteProductById execution started.");
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

            log.debug("ProductService:deleteProductById deleting product from database for id {} {}", productId, ProductValueMapper.jsonAsString(product));
            productRepository.deleteById(productId);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting product {} from database , Exception message {}", productId, ex.getMessage());
            throw new ProductServiceException("Exception occurred while delete product from Database " + productId);
        }
        log.info("ProductService:deleteProductById execution ended.");
    }
}
