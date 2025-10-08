package com.microservices.user_service.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CommonResponse {

    public boolean status;
    public String message;
    public Object response;
    public Instant responseTime;
}
