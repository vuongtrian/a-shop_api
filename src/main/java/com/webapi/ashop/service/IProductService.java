package com.webapi.ashop.service;


import com.webapi.ashop.entity.dto.ProductRequestDTO;
import com.webapi.ashop.entity.dto.ProductResponseDTO;

import java.util.List;

public interface IProductService {
    ProductResponseDTO createNewProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> getProducts();
    ProductResponseDTO getProductById(int productId);
    ProductResponseDTO updateProduct(int productId, ProductRequestDTO productRequestDTO);
    void deleteProductById(int productId);


}
