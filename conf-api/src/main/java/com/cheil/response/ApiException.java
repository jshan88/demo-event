package com.cheil.response;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final ApiResponseCode apiResponseCode;

    public ApiException(ApiResponseCode apiResponseCode){
        this.apiResponseCode = apiResponseCode;
    }
}
