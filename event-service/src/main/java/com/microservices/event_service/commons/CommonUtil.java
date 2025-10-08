package com.microservices.event_service.commons;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommonUtil {

    public static CommonResponse buildCommonResponse(String message, Object response) {
        return CommonResponse.builder()
                .status(true)
                .message(message)
                .response(response)
                .responseTime(Instant.now())
                .build();
    }

    public static CommonResponse buildErrorResponse(String message, Object response) {
        return CommonResponse.builder()
                .status(false)
                .message(message)
                .response(response)
                .responseTime(Instant.now())
                .build();
    }

}
