package com.microservices.event_service.service;

import com.microservices.event_service.commons.CommonResponse;
import com.microservices.event_service.commons.CommonUtil;
import com.microservices.event_service.commons.exceptions.CustomException;
import com.microservices.event_service.entity.Event;
import com.microservices.event_service.entity.EventRegisteredUsers;
import com.microservices.event_service.model.request.EventRequest;
import com.microservices.event_service.model.response.EventRegisteredUserDetailsResponse;
import com.microservices.event_service.model.response.EventResponse;
import com.microservices.event_service.model.response.UserResponse;
import com.microservices.event_service.repository.IEventRegisteredUsersRepository;
import com.microservices.event_service.repository.IEventRepository;
import com.microservices.event_service.service.cache.EventCache;
import com.microservices.event_service.service.validations.EventValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventAdminServiceImpl implements IEventAdminService {

    private final IEventRepository iEventRepository;
    private final IEventRegisteredUsersRepository iEventRegisteredUsersRepository;
    private final EventCache eventCache;
    private final EventValidations validations;
    private final UserFeignClient userFeignClient;

    @Override
    public ResponseEntity<CommonResponse> createEvent(EventRequest eventRequest) {

        validations.checkCreateEventValidations(eventRequest);

        Event event = Event.builder()
                .eventName(eventRequest.eventName())
                .description(eventRequest.description())
                .date(eventRequest.date())
                .fromTime(eventRequest.fromTime())
                .toTime(eventRequest.toTime())
                .url(eventRequest.url())
                .enabled(true)
                .deleted(false)
                .createdTime(Instant.now())
                .modifiedTime(Instant.now())
                .build();

        eventCache.saveEvent(event);

        CommonResponse commonResponse = CommonUtil.buildCommonResponse("SUCCESS", event);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public List<Event> getAllEventDetails() {
        return iEventRepository.findAllByDeletedFalse();
    }

    @Override
    public List<EventRegisteredUserDetailsResponse> getAllEventRegisteredUserDetails() {
        List<EventRegisteredUserDetailsResponse> eventRegisteredUserDetailsResponses = new ArrayList<>();

        List<EventRegisteredUsers> eventRegisteredUsers = iEventRegisteredUsersRepository.findAll();
        if (!eventRegisteredUsers.isEmpty()) {
            eventRegisteredUsers.forEach(a -> {
                EventRegisteredUserDetailsResponse eventRegisteredUserDetailsResponse = EventRegisteredUserDetailsResponse.builder().build();

                Optional<Event> event = iEventRepository.findById(a.getEventId());
                if (!event.isPresent()) {
                    throw new CustomException("Event id doesn't exist");
                }

                UserResponse userResponses = userFeignClient.getUserDetailsByUserId(a.getUserId());


                eventRegisteredUserDetailsResponse.setEventName(event.get().getEventName());
                eventRegisteredUserDetailsResponse.setUsers(userResponses);

                BeanUtils.copyProperties(a, eventRegisteredUserDetailsResponse);
                eventRegisteredUserDetailsResponses.add(eventRegisteredUserDetailsResponse);
            });

        }
        return eventRegisteredUserDetailsResponses;
    }

    @Override
    public EventResponse getAllEventRegisteredUserDetailsByEventId(Long eventId) {
        EventResponse eventResponse = EventResponse.builder().build();

        Optional<Event> event = iEventRepository.findById(eventId);
        if (!event.isPresent()) {
            throw new CustomException("Event id doesn't exist");
        }

        List<Long> eventRegisteredUsers = iEventRegisteredUsersRepository.findUserIdsByEventId(eventId);

        String userIds = eventRegisteredUsers.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(userIds);

        List<UserResponse> userResponses = userFeignClient.getUserDetailsByUserIds(userIds);

        BeanUtils.copyProperties(event.get(), eventResponse);
        eventResponse.setUsers(userResponses);

        return eventResponse;
    }
}
