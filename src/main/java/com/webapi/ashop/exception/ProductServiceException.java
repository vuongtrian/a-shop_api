package com.webapi.ashop.exception;

public class ProductServiceException extends RuntimeException{
    public ProductServiceException(String message) {
        super(message);
    }
}
