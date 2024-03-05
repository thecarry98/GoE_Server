package com.application.learnenglish.exception;

import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@RestControllerAdvice
public class CustomAdviceController extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAdviceController.class);

    private static final String UNKNOWN_ERROR = "The application has encountered an unknown error.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException e) {
        LOGGER.warn("Missing token/authorize. PreAuthorize exception, protected api | " + e.getMessage() + " | " + e.getClass().getName());
        return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> jwtException(JwtException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> restClientException(RestClientException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpException(HttpClientErrorException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getStatusText());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleAllRemainingException(Exception ex) {
        LOGGER.error(UNKNOWN_ERROR, ex);
        return new ResponseEntity<>(new ResponseDTO(UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseDTO> handleAllRemainingException(ResponseStatusException ex) {
        LOGGER.warn("ResponseStatusException: {}", ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), HttpStatus.resolve(ex.getStatusCode().value()), null), ex.getStatusCode());
    }

    @ExceptionHandler(ApplicationRuntimeException.class)
    public ResponseEntity<ResponseDTO> handleAllRemainingException(ApplicationRuntimeException ex) {
        LOGGER.warn("ApplicationRuntimeException: {}", ex.getMessage());
        return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), ex.getHttpStatus(), ex.getData()), ex.getHttpStatus());
    }
}
