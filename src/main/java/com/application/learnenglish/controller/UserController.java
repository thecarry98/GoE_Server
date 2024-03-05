package com.application.learnenglish.controller;

import com.application.learnenglish.exception.ResponseDTO;
import com.application.learnenglish.model.dto.AdminUpdatePW;
import com.application.learnenglish.model.dto.RefreshTokenRequest;
import com.application.learnenglish.model.dto.UpdateUserRequest;
import com.application.learnenglish.model.dto.UserRequest;
import com.application.learnenglish.model.dto.respone.UserResponse;
import com.application.learnenglish.service.RefreshTokenService;
import com.application.learnenglish.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    @PostMapping("/register")
    @Operation(summary = "register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }
    @PutMapping("/users/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UpdateUserRequest updateUserRequest) {
        userService.updatePassWord(updateUserRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/admin/change-password")
    public ResponseEntity<?> adminUpdatePassword(@RequestBody AdminUpdatePW request) {
        userService.adminUpdatePassword(request);
        return new ResponseEntity<>("update password for user successfully!",HttpStatus.OK);
    }
    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getDetailUser(@RequestParam(name = "userId") Long userId) {
        UserResponse userResponse = userService.getUserDTO(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUser() {
        List<UserResponse> userResponses = userService.getAllUser();
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }
    @GetMapping("/count")
    public ResponseEntity<?> countHomePage() {
        return new ResponseEntity<>(userService.count(), HttpStatus.OK);
    }
    @PutMapping("/users")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserRequest request) {
        userService.updateUserProfile(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.logout(refreshTokenRequest.getUsername());
        return new ResponseEntity<>(ResponseDTO.builder().message("Đăng xuất thành công").build(), HttpStatus.OK);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        Map<String, String> token = refreshTokenService.refreshToken(refreshTokenRequest.getUsername(), refreshTokenRequest);
        return new ResponseEntity<>(ResponseDTO.builder().data(token).build(), HttpStatus.OK);
    }
}
