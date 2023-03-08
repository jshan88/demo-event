package com.cheil.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * !주의 : 이 핸들러에서 Response 포맷 수정 시, API Gateway의 익셉션 핸들링 소스 템플릿도 함께 수정한다.
 * ?이유 : Spring GW는 flux 기반이라 spring mvc에서 만든 익셉션 핸들러 사용 못함.
 *
 * @author      : jshan
 * @created     : 2023/03/08
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity handleApiRequestException(ApiException e) {
        return ResponseEntity
                .status(e.getApiResponseCode().getHttpStatus())
                .body(ApiResponseTemplate.createErrorResponse(e.getApiResponseCode()));
    }
}
