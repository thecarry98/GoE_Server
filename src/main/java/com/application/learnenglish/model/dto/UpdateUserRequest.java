package com.application.learnenglish.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotEmpty
    private Long userId;
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
}
