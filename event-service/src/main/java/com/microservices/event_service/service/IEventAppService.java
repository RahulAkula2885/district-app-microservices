package com.microservices.event_service.service;

import com.microservices.event_service.commons.CommonResponse;
import com.microservices.event_service.model.request.EventRegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface IEventAppService {
    ResponseEntity<CommonResponse> registerForEvent(EventRegistrationRequest request);
}
