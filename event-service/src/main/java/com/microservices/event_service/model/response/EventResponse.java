package com.microservices.event_service.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EventResponse {

    private Long id;
    private String eventName;
    private String description;
    private LocalDate date;
    private LocalTime fromTime;
    private LocalTime toTime;
    private String url;
    private boolean enabled;
    private boolean deleted;
    private Instant createdTime;
    private Instant modifiedTime;
    List<UserResponse> users = new ArrayList<>();
}
