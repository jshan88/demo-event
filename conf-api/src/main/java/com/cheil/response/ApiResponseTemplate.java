package com.cheil.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;


/**
 * !주의 : 해당 템플릿 수정 시, API Gateway의 익셉션 핸들링 소스 내 템플릿도 함께 수정한다.
 * ?이유 : Spring GW는 flux 기반이라 spring mvc에서 만든 익셉션 핸들러 사용 못함.
 *
 * @author      : jshan
 * @created     : 2023/03/08
 */
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
