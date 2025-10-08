package com.microservices.user_service.commons.utils;

import com.microservices.user_service.commons.exceptions.CustomException;
import com.microservices.user_service.model.response.CommonResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommonUtil {

    public CommonResponse buildCommonResponse(String message, Object response) {
        return CommonResponse.builder()
                .status(true)
                .message(message)
                .response(response)
                .responseTime(Instant.now())
                .build();
    }

    public CommonResponse buildErrorResponse(String message, Object response) {
        return CommonResponse.builder()
                .status(false)
                .message(message)
                .response(response)
                .responseTime(Instant.now())
                .build();
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object obj = authentication.getCredentials();
            if (obj instanceof Long userId) {
                return userId;
            }
        } else {
            throw new CustomException("Something went wrong");
        }
        return null; // Or throw an exception if needed
    }
}
