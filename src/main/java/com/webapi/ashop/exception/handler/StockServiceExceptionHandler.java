package com.webapi.ashop.exception.handler;

import com.webapi.ashop.entity.dto.APIResponse;
import com.webapi.ashop.entity.dto.ErrorDTO;
import com.webapi.ashop.exception.StockNotFoundException;
import com.webapi.ashop.exception.StockServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class StockServiceExceptionHandler extends CustomizedExceptionHandler{
    @ExceptionHandler(StockServiceException.class)
    public APIResponse<?> handleServiceException(StockServiceException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(StockNotFoundException.class)
    public APIResponse<?> handleProductNotFoundException(StockNotFoundException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }
}
