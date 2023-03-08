package com.cheil.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * !!!Description here
 *
 * @author : jshan
 * @created : 2023/03/08
 */
@Getter
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

    @Builder
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
