package com.application.learnenglish.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("exception", exception.getMessage());
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(objectMapper.writeValueAsString(data));
    }
}
