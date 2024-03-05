package com.application.learnenglish.service.implement;

import com.application.learnenglish.controller.ImageController;
import com.application.learnenglish.exception.ApplicationRuntimeException;
import com.application.learnenglish.service.MinIoService;
import com.application.learnenglish.util.MessageManager;
import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import io.minio.*;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinIoServiceImpl implements MinIoService {

    private final MinioClient minioClient;
    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${tinify.api.key}")
    private String apiKey;
    private final MessageManager messageManager;

    private final Logger LOGGER = LoggerFactory.getLogger(MinIoServiceImpl.class);

    @Override
    public String uploadImage(BufferedImage image) {
        String fileName = UUID.randomUUID() + ".jpeg";
        try {
            var width = Math.min(image.getWidth(), 1920);
            byte[] bytes;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(image)
                    .width(width)
                    .keepAspectRatio(true)
                    .outputFormat("jpeg")
                    .toOutputStream(outputStream);
            bytes = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(new BufferedInputStream(inputStream), bytes.length, -1)
                    .bucket(bucketName)
                    .object(fileName)
                    .contentType("image/jpeg")
                    .build());
            return fileName;
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Error when upload image to minio");
        }

    }

    @Override
    public String objectUpload(MultipartFile file) {
        String uuidImage = UUID.randomUUID().toString();
        String object = uuidImage + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            var imageType = object.substring(object.lastIndexOf(".") + 1);
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            var width = Math.min(originalImage.getWidth(), 1920);
            byte[] bytes;
            if ("png".equalsIgnoreCase(imageType) && originalImage.getWidth() > 1920) {
                Tinify.setKey(apiKey);
                Source source = Tinify.fromBuffer(file.getBytes());
                Options options = new Options()
                        .with("method", "scale")
                        .with("width", width);
                Source resized = source.resize(options);
                bytes = resized.toBuffer();
            } else {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Thumbnails.of(originalImage)
                        .width(width)
                        .keepAspectRatio(true)
                        .outputFormat(imageType)
                        .toOutputStream(outputStream);
                bytes = outputStream.toByteArray();
            }
            InputStream inputStream = new ByteArrayInputStream(bytes);
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(new BufferedInputStream(inputStream), bytes.length, -1)
                    .bucket(bucketName)
                    .object(object)
                    .contentType("image/jpeg")
                    .build());
            return object;
        } catch (Exception e) {
            LOGGER.error("Error while upload image to Minio: {}", e.getMessage(), e);
            throw new ApplicationRuntimeException(messageManager.buildErrorMessage("ERROR_UPLOAD_IMAGE"), e);
        }
    }


    @Override
    public String uploadFile(MultipartFile file) {
        String objectName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .stream(new BufferedInputStream(inputStream), file.getSize(), -1)
                    .bucket(bucketName)
                    .object(objectName)
                    .contentType(file.getContentType())
                    .build());
            return objectName;
        } catch (Exception e) {
            LOGGER.error("Error while upload file to Minio: {}", e.getMessage(), e);
            throw new ApplicationRuntimeException(messageManager.buildErrorMessage("ERROR_UPLOAD_FILE"), e);
        }
    }

    @Override
    public InputStream getObject(String filename) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (Exception ex) {
            LOGGER.error("Error while get image {}", filename, ex);
            throw new ApplicationRuntimeException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean bucketExists() {
        try {
            BucketExistsArgs args = BucketExistsArgs.builder().bucket(bucketName).build();
            boolean isBucketExists = minioClient.bucketExists(args);

            if (!isBucketExists) {
                throw new ApplicationRuntimeException(messageManager.buildErrorMessage("ERROR_BUCKET_EXIST"));
            }
            return isBucketExists;
        } catch (Exception e) {
            LOGGER.error("Error while check exist bucket of Minio: {}", e.getMessage(), e);
            throw new ApplicationRuntimeException(messageManager.buildErrorMessage("ERROR_UPLOAD_IMAGE"), e);
        }
    }

    @Override
    public boolean verifyBucket() {
        try {
            boolean bucketExists = bucketExists();

            ListObjectsArgs listArgs = ListObjectsArgs.builder().bucket(bucketName).build();
            Iterable<Result<Item>> results = minioClient.listObjects(listArgs);
            for (Result<Item> result : results) {
            }
            return bucketExists;
        } catch (Exception e) {
            LOGGER.error("Error while check exist bucket of Minio: {}", e.getMessage(), e);
            throw new ApplicationRuntimeException(messageManager.buildErrorMessage("ERROR_UPLOAD_IMAGE"), e);
        }
    }

    public void removeObject(String object) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(object)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            throw new ApplicationRuntimeException("Error when clean file minio");
        }

    }
    @Override
    public Map<String, String> uploadImage(MultipartFile multipartFile) {
        this.bucketExists();
        String fileName = this.objectUpload(multipartFile);
        String url = MvcUriComponentsBuilder
                .fromMethodName(ImageController.class, "getImage", fileName).build().toString();
        return Map.of("urlImage", url);
    }
}
