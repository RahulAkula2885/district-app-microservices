package com.microservices.event_service.controller;

import com.microservices.event_service.commons.CommonResponse;
import com.microservices.event_service.model.request.EventRegistrationRequest;
import com.microservices.event_service.service.IEventAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/app/event")
@RequiredArgsConstructor
public class EventAppController {

    private final IEventAppService iEventAppService;

    @PostMapping("register")
    public ResponseEntity<CommonResponse> registerForEvent(@RequestBody EventRegistrationRequest request) {
        return iEventAppService.registerForEvent(request);
    }
}
