package com.application.learnenglish.model.dto.respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse implements Serializable {
    private Long id;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    private String avatar;
}
