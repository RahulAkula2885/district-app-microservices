package com.microservices.event_service.model.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventRequest(String eventName, String description, LocalDate date, LocalTime fromTime, LocalTime toTime, String url) {
}
