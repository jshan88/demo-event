package com.cheil.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity handleApiRequestException(ApiException e) {
        return ResponseEntity
                .status(e.getApiResponseCode().getHttpStatus())
                .body(ApiResponseTemplate.createErrorResponse(e.getApiResponseCode()));
    }
}
