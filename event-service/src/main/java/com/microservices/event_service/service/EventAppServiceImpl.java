package com.microservices.event_service.service;

import com.microservices.event_service.commons.CommonResponse;
import com.microservices.event_service.commons.CommonUtil;
import com.microservices.event_service.entity.EventRegisteredUsers;
import com.microservices.event_service.model.request.EventRegistrationRequest;
import com.microservices.event_service.repository.IEventRepository;
import com.microservices.event_service.service.cache.EventCache;
import com.microservices.event_service.service.validations.EventValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventAppServiceImpl implements IEventAppService {

    private final EventValidations validations;
    private final IEventRepository iEventRepository;
    private final EventCache cache;

    @Override
    public ResponseEntity<CommonResponse> registerForEvent(EventRegistrationRequest request) {

        validations.checkValidationsForEventRegistration(request);

        EventRegisteredUsers eventRegisteredUsers = EventRegisteredUsers.builder()
                .eventId(request.eventId())
                .userId(request.userId())
                .registered(true)
                .createdTime(Instant.now())
                .modifiedTime(Instant.now())
                .build();

        cache.registerForEvent(eventRegisteredUsers);

        CommonResponse commonResponse = CommonUtil.buildCommonResponse("SUCCESS", eventRegisteredUsers);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
