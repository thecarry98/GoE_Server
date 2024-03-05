package com.application.learnenglish.config.security.jwt;

import lombok.Data;

import java.util.Collection;

@Data
public class JwtResponse {
    private Long userId;
    private String username;
    private String displayName;
    private Long expiration;
    private String token;
    private String refreshToken;
    private Collection role;
    private String photoURL;
    // todo
    public JwtResponse(Long userId,
                       String username,
                       String displayName,
                       Long expiration,
                       String token,
                       String refreshToken,
                       String photoUrl,
                       Collection role) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.expiration = expiration;
        this.token = token;
        this.refreshToken = refreshToken;
        this.photoURL = photoUrl;
        this.role = role;
    }
}
