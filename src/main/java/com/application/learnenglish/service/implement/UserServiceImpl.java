package com.application.learnenglish.service.implement;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.AdminUpdatePW;
import com.application.learnenglish.model.dto.ResponseMessage;
import com.application.learnenglish.model.dto.UpdateUserRequest;
import com.application.learnenglish.model.dto.UserRequest;
import com.application.learnenglish.model.dto.respone.UserResponse;
import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.model.enums.Role;
import com.application.learnenglish.repository.BookRepository;
import com.application.learnenglish.repository.PostRepository;
import com.application.learnenglish.repository.SpeakingRepository;
import com.application.learnenglish.repository.UserRepository;
import com.application.learnenglish.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final SpeakingRepository speakingRepo;
    private final BookRepository bookRepo;
    private final PasswordEncoder bCrypt;
    @Override
    public ResponseEntity<?> registerUser(UserRequest request) {
        if (ObjectUtils.isNotEmpty(userRepo.findByEmail(request.getEmail()))) {
            throw new ApplicationRuntimeException("Email already!");
        }
        if (ObjectUtils.isNotEmpty(userRepo.findByUserName(request.getUsername()))) {
            throw new ApplicationRuntimeException("User already!");
        }
        Role role;
        if (!StringUtils.isEmpty(request.getRole())) {
            role = Role.getByShortName(request.getRole());
            if (Objects.isNull(role)) {
                throw new ApplicationRuntimeException("Role không hợp lệ", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ApplicationRuntimeException("Role không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        String hashPw = bCrypt.encode(request.getPassword());
        User user = User.builder()
                .fullName(request.getFullName())
                .userName(request.getUsername())
                .email(request.getEmail())
                .avatar(request.getAvatar())
                .password(hashPw)
                .role(role)
                .build();
        userRepo.save(user);
        return ResponseEntity.ok()
                .body(new ResponseMessage<>("Register successfully!", HttpStatus.OK));
    }

    @Override
    public void updatePassWord(UpdateUserRequest updateUserRequest) {
        User user = userRepo.findById(updateUserRequest.getUserId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("User not exit");
        });
        String passwordEncoder = bCrypt.encode(updateUserRequest.getNewPassword());
        if (BooleanUtils.isFalse(bCrypt.matches(updateUserRequest.getOldPassword(), user.getPassword()))) {
            throw new ApplicationRuntimeException("Old password not matched", HttpStatus.BAD_REQUEST);
        }
        if (BooleanUtils.isTrue(bCrypt.matches(updateUserRequest.getNewPassword(), user.getPassword()))) {
            throw new ApplicationRuntimeException("Old password and new password are not the same", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder);
        userRepo.save(user);
    }

    @Override
    public void updateUserProfile(UserRequest request) {
        User user = userRepo.findById(request.getId()).orElseThrow(() -> {
            throw new ApplicationRuntimeException("User not exit");
        });
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setAvatar(StringUtils.isEmpty(request.getAvatar()) ? user.getAvatar() : request.getAvatar());
        userRepo.save(user);
    }

    @Override
    public UserResponse getUserDTO(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> {
            throw new ApplicationRuntimeException("User not exit");
        });
        return UserResponse.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .username(user.getUserName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepo.findByRole(Role.USER);
        if (ObjectUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream()
                .sorted(Comparator.comparing(User::getId))
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUserName())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .avatar(user.getAvatar())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void adminUpdatePassword(AdminUpdatePW request) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (admin.getRole().equals(Role.ADMINISTRATOR)) {
            User user = userRepo.findById(request.getUserId()).orElseThrow(() -> {
                throw new ApplicationRuntimeException("User not exit");
            });
            String passwordEncoder = bCrypt.encode(request.getNewPassword());
            user.setPassword(passwordEncoder);
            userRepo.save(user);
        }
        else {
            throw new ApplicationRuntimeException("Role permission denied");
        }
    }

    @Override
    public Map<String, Long> count() {
        Long countTotalUser = userRepo.count();
        Long countTotalBook = bookRepo.count();
        Long countTotalSpeaking = speakingRepo.count();
        Map<String, Long> count = new HashMap<>();
        count.put("Count total user", countTotalUser);
        count.put("Count total book", countTotalBook);
        count.put("Count total speaking", countTotalSpeaking);
        return count;
    }

}
