package com.cheil.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserGetParam {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    @Builder
    public UserGetParam(Long id, String email, String firstName, String lastName){
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
