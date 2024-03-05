package com.application.learnenglish.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;

@Data
@AllArgsConstructor
public class UserContext {
    private Authentication authentication;
}

