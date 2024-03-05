package com.application.learnenglish.service;


import com.application.learnenglish.model.dto.RefreshTokenRequest;

import java.util.Map;

public interface RefreshTokenService {
    void logout(String username);

    Map<String, String> refreshToken(String username, RefreshTokenRequest refreshTokenRequest);

    void clearRefreshToken();
}
