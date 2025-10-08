package com.microservices.event_service.model.request;

public record EventRegistrationRequest(Long eventId, Long userId) {
}
