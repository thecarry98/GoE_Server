package com.application.learnenglish.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Map;

public interface MinIoService {

    String uploadImage(BufferedImage image);

    String objectUpload(MultipartFile file);

    String uploadFile(MultipartFile file);

    InputStream getObject(String filename);

    boolean bucketExists();

    boolean verifyBucket();
    Map<String, String> uploadImage(MultipartFile multipartFile);
}
