package com.application.learnenglish.config.security.auth_token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class ApplicationAuthToken extends UsernamePasswordAuthenticationToken {
    private String user;
    private String password;

    public ApplicationAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
