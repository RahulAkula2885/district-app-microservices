package com.microservices.event_service.commons.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.microservices.event_service.commons.CommonResponse;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalException implements ErrorController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> customException(CustomException ex) {
        log.info("CustomException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> accessDeniedException(AccessDeniedException ex) {
        log.info("AccessDeniedException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<CommonResponse> handleForbiddenException(HttpClientErrorException.Forbidden ex) {
        log.info("HttpClientErrorException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("MethodArgumentNotValidException");
        // Collect validation errors
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Create a structured response
        CommonResponse response = CommonResponse.builder()
                .status(false)
                .message("Validation failed")
                .response(errors)
                .responseTime(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<CommonResponse> createConnectException(ConnectException ex) {
        log.info("ConnectException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<CommonResponse> createResourceAccessException(ResourceAccessException ex) {
        log.info("ResourceAccessException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.info("HttpRequestMethodNotSupportedException");
        HttpMethod supportedMethod = Objects.requireNonNull(ex.getSupportedHttpMethods()).iterator().next();
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<CommonResponse> notFoundException(NoResultException ex) {
        log.info("NoResultException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<CommonResponse> iOException(IOException ex) {
        log.info("IOException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse> internalServerException(IllegalArgumentException ex) {
        log.info("IllegalArgumentException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<CommonResponse> handleGenericException(TokenExpiredException ex) {
        log.info("TokenExpiredException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericException(Exception ex) {
        log.info("Exception");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResponse> handleRuntimeException(RuntimeException ex) {
        log.info("RuntimeException");
        CommonResponse commonResponse = buildCommonResponse(ex.getMessage());
        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private CommonResponse buildCommonResponse(String message) {
        return CommonResponse.builder()
                .status(false)
                .message(message)
                .response(null)
                .responseTime(Instant.now())
                .build();
    }
}
