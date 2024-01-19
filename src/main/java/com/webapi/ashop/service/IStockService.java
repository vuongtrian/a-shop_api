package com.webapi.ashop.service;

import com.webapi.ashop.entity.dto.StockRequestDTO;
import com.webapi.ashop.entity.dto.StockResponseDTO;

import java.util.List;

public interface IStockService {
    StockResponseDTO createNewStock(StockRequestDTO stockRequestDTO);
    List<StockResponseDTO> getStocks();
    StockResponseDTO getStockById(int stockId);
    StockResponseDTO updateStock(int stockId, StockRequestDTO stockRequestDTO);
    void deleteStockById(int stockId);
}
