package com.microservices.event_service.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EventRegisteredUserDetailsResponse {

    private Long id;
    private Long eventId;
    private String eventName;
    private Long userId;
    private boolean registered;
    private Instant createdTime;
    private Instant modifiedTime;
    UserResponse users;
}
