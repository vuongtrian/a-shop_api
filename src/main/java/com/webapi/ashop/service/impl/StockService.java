package com.webapi.ashop.service.impl;

import com.webapi.ashop.entity.dto.StockRequestDTO;
import com.webapi.ashop.entity.dto.StockResponseDTO;
import com.webapi.ashop.service.IStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StockService implements IStockService {
    @Override
    public StockResponseDTO createNewStock(StockRequestDTO stockRequestDTO) {
        return null;
    }

    @Override
    public List<StockResponseDTO> getStocks() {
        return null;
    }

    @Override
    public StockResponseDTO getStockById(int stockId) {
        return null;
    }

    @Override
    public StockResponseDTO updateStock(int stockId, StockRequestDTO stockRequestDTO) {
        return null;
    }

    @Override
    public void deleteStockById(int stockId) {

    }
}
