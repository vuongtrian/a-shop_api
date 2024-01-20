package com.webapi.ashop.service.impl;

import com.webapi.ashop.entity.Stock;
import com.webapi.ashop.entity.dto.StockRequestDTO;
import com.webapi.ashop.entity.dto.StockResponseDTO;
import com.webapi.ashop.exception.StockNotFoundException;
import com.webapi.ashop.exception.StockServiceException;
import com.webapi.ashop.repository.IStockRepository;
import com.webapi.ashop.service.IStockService;
import com.webapi.ashop.util.ProductValueMapper;
import com.webapi.ashop.util.StockValueMapper;
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
public class StockService implements IStockService {

    @Autowired
    private IStockRepository stockRepository;

//    @Autowired
//    private IFileDataService iFileDataService;

    @Override
    @CacheEvict(value = "stock", key = "'allStocks'")
    public StockResponseDTO createNewStock(StockRequestDTO stockRequestDTO) throws StockServiceException {
        StockResponseDTO stockResponseDTO;

        try {
            log.info("StockService:createNewStock execution started.");
            Stock stock = StockValueMapper.convertToEntity(stockRequestDTO);
            log.debug("StockService:createNewStock request parameters {}", StockValueMapper.jsonAsString(stockRequestDTO));
            Stock stockResults = stockRepository.save(stock);
            stockResponseDTO = StockValueMapper.convertToDTO(stockResults);
            log.debug("StockService:createNewStock received response from Database {}", ProductValueMapper.jsonAsString(stockResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while persisting stock to database , Exception message {}", ex.getMessage());
            throw new StockServiceException("Exception occurred while create a new stock");
        }
        log.info("StockService:createNewStock execution ended.");
        return stockResponseDTO;
    }

    @Override
    @Cacheable(value = "stock", key = "'allStocks'")
    public List<StockResponseDTO> getStocks() throws StockServiceException{
        List<StockResponseDTO> stockResponseDTOS;
        try {
            log.info("StockService:getStocks execution started.");
            List<Stock> stockList = stockRepository.findAll();
            if(!stockList.isEmpty()) {
                stockResponseDTOS = stockList.stream()
                        .map(StockValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                stockResponseDTOS = Collections.emptyList();
            }
            log.debug("StockService:getStocks retrieving stocks from database  {}", StockValueMapper.jsonAsString(stockResponseDTOS));
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving stocks from database , Exception message {}", ex.getMessage());
            throw new StockServiceException("Exception occurred while fetch all stocks from Database");
        }
        log.info("StockService:getStocks execution ended.");
        return stockResponseDTOS;
    }

    @Override
    @Cacheable(value = "stock", key = "#stockId")
    public StockResponseDTO getStockById(int stockId) {
        StockResponseDTO stockResponseDTO;

        try {
            log.info("StockService:getStockById execution started.");
            Stock stock = stockRepository.findById(stockId)
                    .orElseThrow(() -> new StockNotFoundException("Stock not found with id " + stockId));
            stockResponseDTO = StockValueMapper.convertToDTO(stock);
            log.debug("StockService:getStockById retrieving stock from database for id {} {}", stockId, StockValueMapper.jsonAsString(stockResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while retrieving stock {} from database , Exception message {}", stockId, ex.getMessage());
            throw new StockServiceException("Exception occurred while fetch stock from Database " + stockId);
        }
        log.info("StockService:getStockById execution ended.");
        return stockResponseDTO;
    }

    @Override
    @Caching(put = {
            @CachePut(value = "stock", key = "#stockId")
    }, evict = {
            @CacheEvict(value = "stock", key = "'allStocks'")
    })
    public StockResponseDTO updateStock(int stockId, StockRequestDTO stockRequestDTO) {
        StockResponseDTO stockResponseDTO;
        try {
            log.info("StockService:updateStock execution started.");
            Stock stock = stockRepository.findById(stockId)
                    .orElseThrow(() -> new StockNotFoundException("Stock not found with id " + stockId));
            stock.setColor(stockRequestDTO.getColor());
            stock.setSize(stockRequestDTO.getSize());
            stock.setQuantity(stockRequestDTO.getQuantity());
            stock.setImages(stockRequestDTO.getImages());
            log.debug("StockService:updateStock request parameters {}", StockValueMapper.jsonAsString(stockRequestDTO));

            Stock stockResults = stockRepository.save(stock);
            stockResponseDTO = StockValueMapper.convertToDTO(stockResults);
            log.debug("StockService:updateStock received response from Database {}", StockValueMapper.jsonAsString(stockResponseDTO));
        } catch (Exception ex) {
            log.error("Exception occurred while update stock from database , Exception message {}", ex.getMessage());
            throw new StockServiceException("Exception occurred while update stock");
        }
        log.info("StockService:updateStock execution ended.");
        return stockResponseDTO;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "stock", key = "#stockId"),
            @CacheEvict(value = "stock", key = "'allStocks'")
    })
    public void deleteStockById(int stockId) {
        try {
            log.info("StockService:deleteStockById execution started.");
            Stock stock = stockRepository.findById(stockId)
                    .orElseThrow(() -> new StockNotFoundException("Stock not found with id " + stockId));
            log.debug("StockService:deleteStockById deleting stock from database for id {} {}", stockId, StockValueMapper.jsonAsString(stock));
            stockRepository.deleteById(stockId);
        } catch (Exception ex) {
            log.error("Exception occurred while deleting stock {} from database , Exception message {}", stockId, ex.getMessage());
            throw new StockServiceException("Exception occurred while delete stock from Database " + stockId);
        }
        log.info("StockService:deleteStockById execution ended.");
    }
}
