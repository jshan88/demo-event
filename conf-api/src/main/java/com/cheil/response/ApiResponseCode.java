package com.cheil.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {

    // 403 Forbidden
    PASSWORD_INCORRECT(HttpStatus.FORBIDDEN, "Incorrect Password"),
    // ...

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "No User Found for the given parameters"),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "User already exist");
    private final HttpStatus httpStatus;
    private final String responseMessage;

}
