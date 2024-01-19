package com.webapi.ashop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapi.ashop.entity.Stock;
import com.webapi.ashop.entity.dto.StockRequestDTO;
import com.webapi.ashop.entity.dto.StockResponseDTO;

public class StockValueMapper {
    public static Stock convertToEntity(StockRequestDTO stockRequestDTO) {
        Stock stock = new Stock();
        stock.setColor(stockRequestDTO.getColor());
        stock.setSize(stockRequestDTO.getSize());
        stock.setQuantity(stockRequestDTO.getQuantity());
        stock.setImages(stockRequestDTO.getImages());
        return stock;
    }

    public static StockResponseDTO convertToDTO(Stock stock) {
        StockResponseDTO stockResponseDTO = new StockResponseDTO();
        stockResponseDTO.setId(stock.getId());
        stockResponseDTO.setColor(stock.getColor());
        stockResponseDTO.setSize(stock.getSize());
        stockResponseDTO.setQuantity(stock.getQuantity());
        stockResponseDTO.setImages(FileDataValueMapper.convertToListDTO(stock.getImages()));
        return stockResponseDTO;
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
