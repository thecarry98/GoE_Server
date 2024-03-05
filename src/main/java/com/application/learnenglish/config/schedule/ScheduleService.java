package com.application.learnenglish.config.schedule;

import com.application.learnenglish.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final RefreshTokenService refreshTokenService;
    @Scheduled(cron = "0 0 0 ? * MON")
    @Transactional
    public void clearRefreshToken() {
        refreshTokenService.clearRefreshToken();
    }
}
