package com.cheil.user.dto;

import com.cheil.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String email;
    private String firstName;
    private String lastName;

    @Builder
    public UserJoinRequest(String email, String firstName, String lastName){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User toUser(){
        return User.builder()
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }
}
