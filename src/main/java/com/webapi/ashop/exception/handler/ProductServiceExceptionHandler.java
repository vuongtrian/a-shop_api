package com.webapi.ashop.exception.handler;

import com.webapi.ashop.entity.dto.APIResponse;
import com.webapi.ashop.entity.dto.ErrorDTO;
import com.webapi.ashop.exception.ProductNotFoundException;
import com.webapi.ashop.exception.ProductServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class ProductServiceExceptionHandler extends CustomizedExceptionHandler{
    @ExceptionHandler(ProductServiceException.class)
    public APIResponse<?> handleServiceException(ProductServiceException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public APIResponse<?> handleProductNotFoundException(ProductNotFoundException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }
}
