package com.cheil.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class ApiResponseTemplate<T> {
    private String returnCode;
    private String returnMessage;
    private T data;

    @Builder
    public ApiResponseTemplate(String returnCode, String returnMessage, T data){
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
    }

    public static ApiResponseTemplate createErrorResponse(ApiResponseCode apiResponseCode) {
        return ApiResponseTemplate.builder()
                .returnCode("ERROR")
                .returnMessage(apiResponseCode.getResponseMessage())
                .data(null)
                .build();
    }

    public static <T> ApiResponseTemplate createSuccessResponse(T data) {
        return ApiResponseTemplate.builder()
                .returnCode("SUCCESS")
                .returnMessage("")
                .data(data)
                .build();
    }
}
