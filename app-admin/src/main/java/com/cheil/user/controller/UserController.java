package com.cheil.user.controller;

import com.cheil.response.ApiResponseTemplate;
import com.cheil.user.dto.UserGetParam;
import com.cheil.user.dto.UserJoinRequest;
import com.cheil.user.dto.UserResponse;
import com.cheil.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponseTemplate<List<UserResponse>> getUsers(@ModelAttribute UserGetParam userGetParam){
        return ApiResponseTemplate.createSuccessResponse(userService.getUsers(userGetParam));
    }

    @PostMapping
    public ApiResponseTemplate<UserResponse> createUser(@RequestBody UserJoinRequest userJoinRequest){
        return ApiResponseTemplate.createSuccessResponse(userService.createUser(userJoinRequest));
    }

}
