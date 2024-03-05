package com.application.learnenglish.service;

import com.application.learnenglish.model.dto.AdminUpdatePW;
import com.application.learnenglish.model.dto.UpdateUserRequest;
import com.application.learnenglish.model.dto.UserRequest;
import com.application.learnenglish.model.dto.respone.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<?> registerUser(UserRequest userRequest);
    void updatePassWord(UpdateUserRequest updateUserRequest);
    void updateUserProfile(UserRequest userRequest);
    UserResponse getUserDTO(Long userId);
    List<UserResponse> getAllUser();
    void adminUpdatePassword(AdminUpdatePW request);
    Map<String, Long> count();
}
