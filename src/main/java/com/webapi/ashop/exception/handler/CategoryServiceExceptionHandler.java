package com.webapi.ashop.exception.handler;

import com.webapi.ashop.entity.dto.APIResponse;
import com.webapi.ashop.entity.dto.ErrorDTO;
import com.webapi.ashop.exception.CategoryNotFoundException;
import com.webapi.ashop.exception.CategoryServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class CategoryServiceExceptionHandler extends CustomizedExceptionHandler{
    @ExceptionHandler(CategoryServiceException.class)
    public APIResponse<?> handleServiceException(CategoryServiceException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public APIResponse<?> handleProductNotFoundException(CategoryNotFoundException exception) {
        APIResponse<?> serviceResponse = new APIResponse<>();
        serviceResponse.setStatus("FAILED");
        serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
        return serviceResponse;
    }
}
