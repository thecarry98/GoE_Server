package com.application.learnenglish.controller;

import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.service.MinIoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
@Tag(name = "Image controller")
public class ImageController {
    private final MinIoService minIoService;

    @GetMapping("/image/{image:.+}")
    @Operation(summary = "get image")
    public ResponseEntity<?> getImage(@PathVariable("image") String image) {
        if (ObjectUtils.isEmpty(minIoService.getObject(image))) {
            throw new ApplicationRuntimeException("Image not exits", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png; filename=\"" + image + "\"").body(new InputStreamResource(minIoService.getObject(image)));
    }
    @GetMapping("/file/{file:.+}")
    public ResponseEntity<?> getFile(@PathVariable("file") String file) {
        if (ObjectUtils.isEmpty(minIoService.getObject(file))) {
            throw new ApplicationRuntimeException("File not exits", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf; filename=\"" + file + "\"")
                .body(new InputStreamResource(minIoService.getObject(file)));
    }
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(minIoService.uploadImage(multipartFile), HttpStatus.OK);
    }
}
