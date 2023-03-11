package com.cheil.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/11
 */
@Getter
@NoArgsConstructor
public class AuthResponseTemplate<T> {

    String returnCode;
    String returnMessage;
    T data;

    @Builder
    public AuthResponseTemplate(String returnCode, String returnMessage, T data) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.data = data;
    }

    public static AuthResponseTemplate createErrorResponse(String returnMessage) {
        return AuthResponseTemplate.builder().returnCode("ERROR").returnMessage(returnMessage)
            .data(null)
            .build();
    }

    public static <T> AuthResponseTemplate createSuccessResponse(T data) {
        return AuthResponseTemplate.builder().returnCode("SUCCESS").returnMessage("").data(data)
            .build();
    }
}
