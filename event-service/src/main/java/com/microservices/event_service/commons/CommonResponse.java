package com.microservices.event_service.commons;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

//@Data
@Builder
public record CommonResponse(boolean status, String message, Object response,Instant responseTime) {

//    public boolean status;
//    public String message;
//    public Object response;
//    public Instant responseTime;
}
