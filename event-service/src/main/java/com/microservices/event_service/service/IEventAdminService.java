package com.microservices.event_service.service;

import com.microservices.event_service.commons.CommonResponse;
import com.microservices.event_service.entity.Event;
import com.microservices.event_service.model.request.EventRequest;
import com.microservices.event_service.model.response.EventRegisteredUserDetailsResponse;
import com.microservices.event_service.model.response.EventResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEventAdminService {

    ResponseEntity<CommonResponse> createEvent(EventRequest eventRequest);

    List<Event> getAllEventDetails();

    List<EventRegisteredUserDetailsResponse> getAllEventRegisteredUserDetails();

    EventResponse getAllEventRegisteredUserDetailsByEventId(Long eventId);
}
