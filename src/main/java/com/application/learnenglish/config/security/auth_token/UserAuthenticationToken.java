package com.application.learnenglish.config.security.auth_token;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserAuthenticationToken {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserAuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
