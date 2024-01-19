package com.webapi.ashop.exception;

public class StockServiceException extends RuntimeException{
    public StockServiceException(String message) {
        super(message);
    }
}
