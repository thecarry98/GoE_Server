package com.application.learnenglish.controller;

import com.application.learnenglish.service.implement.NewServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
@Tag(name = "News Controller")
public class NewsController {
    private final NewServiceImpl newService;

    @GetMapping("/news")
    public ResponseEntity<?> getNews() {
        return new ResponseEntity<>(newService.getAllNews(), HttpStatus.OK);
    }

}
