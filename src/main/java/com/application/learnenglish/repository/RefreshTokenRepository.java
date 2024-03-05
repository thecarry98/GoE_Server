package com.application.learnenglish.repository;

import com.application.learnenglish.model.entity.RefreshToken;
import com.application.learnenglish.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByRefreshTokenCode(String refreshToken);

    List<RefreshToken> findAllByUser(User user);
}
