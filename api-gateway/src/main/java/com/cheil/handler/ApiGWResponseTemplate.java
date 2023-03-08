package com.cheil.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * !!! API 공통 응답 템플릿 바꾸면 여기도 바꿔야됨.
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@Getter
@NoArgsConstructor
public class ApiGWResponseTemplate<T> {

    private String returnCode;
    private String returnMessage;
    private T data;

    @Builder
    public ApiGWResponseTemplate(String returnCode, String returnMessage, T data) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
    }
}
