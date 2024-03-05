package com.application.learnenglish.service.implement;

import com.application.learnenglish.config.security.jwt.JwtTokenUtils;
import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.model.dto.RefreshTokenRequest;
import com.application.learnenglish.model.entity.RefreshToken;
import com.application.learnenglish.model.entity.User;
import com.application.learnenglish.repository.RefreshTokenRepository;
import com.application.learnenglish.repository.UserRepository;
import com.application.learnenglish.service.RefreshTokenService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${application.jwtExpiration}")
    private int jwtExpirationMs;
    @Value("${application.jwtExpirationRefreshToken}")
    private long jwtExpirationRefreshToken;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    private final String MESSAGE_END_LOGIN_SESSION = "Phiên đăng nhập hết hạn, vui lòng liên hệ admin";


    @Override
    public void logout(String username) {
        User user = userRepository.findByUserName(username);
        List<RefreshToken> refreshTokens = refreshTokenRepository.findAllByUser(user);
        refreshTokenRepository.deleteAll(refreshTokens);
    }

    @Override
    public Map<String, String> refreshToken(String username, RefreshTokenRequest refreshTokenRequest) {
        try {
            jwtTokenUtils.validateRefreshJwtToken(refreshTokenRequest.getRefreshToken());
            RefreshToken refreshToken = refreshTokenRepository.findByRefreshTokenCode(refreshTokenRequest.getRefreshToken());
            if (ObjectUtils.isEmpty(refreshToken)) {
                throw new ApplicationRuntimeException(MESSAGE_END_LOGIN_SESSION, HttpStatus.UNAUTHORIZED);
            }
            User user = userRepository.findByUserName(username);
            Map<String, Object> claims = new HashMap<>();
            List<GrantedAuthority> authorities = Collections.
                    singletonList(new SimpleGrantedAuthority(user.getRole().name()));
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getUserName())
                    .setIssuedAt(new Date())
                    .addClaims(Map.of("authorities", authorities))
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(jwtTokenUtils.getKey()).compact();
            String refreshTokenNew = jwtTokenUtils.generateJwtTokenRefresh();
            refreshTokenRepository.save(RefreshToken.builder()
                    .refreshTokenCode(refreshTokenNew)
                    .user(user)
                    .expiryDate(Instant.now().plusMillis(jwtExpirationRefreshToken))
                    .build());
            return Map.of("token", token, "refreshToken", refreshTokenNew);
        } catch (Exception e) {
            throw new ApplicationRuntimeException(MESSAGE_END_LOGIN_SESSION, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void clearRefreshToken() {
        refreshTokenRepository.deleteAll();
    }
}
