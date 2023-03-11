package com.cheil.user.service;

import com.cheil.response.ApiException;
import com.cheil.response.ApiResponseCode;
import com.cheil.user.domain.User;
import com.cheil.user.dto.UserGetParam;
import com.cheil.user.dto.UserJoinRequest;
import com.cheil.user.dto.UserResponse;
import com.cheil.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getUsers(UserGetParam userGetParam){
        List<User> userList = userRepository.findUsersByParams(userGetParam.getId(), userGetParam.getEmail(),
                userGetParam.getFirstName(), userGetParam.getLastName());

        // if userList empty then throws exception.
        if(userList.isEmpty()) {
            throw new ApiException(ApiResponseCode.USER_NOT_FOUND);
        }

        List<UserResponse> userResponseList = new ArrayList<>();
        userList.forEach(user -> userResponseList.add(toUserResponse(user)));

        return userResponseList;
    }

    public UserResponse createUser(UserJoinRequest userJoinRequest){
        User user = userJoinRequest.toUser();

        //Unique key 위반 케이스 (DataIntegrityViolationException) 발생 처리
        try {
            userRepository.save(user);
        }catch(DataIntegrityViolationException e){
            throw new ApiException(ApiResponseCode.USER_ALREADY_EXIST);
        }
        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
