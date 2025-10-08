package com.microservices.event_service.controller;

import com.microservices.event_service.commons.CommonResponse;
import com.microservices.event_service.entity.Event;
import com.microservices.event_service.model.request.EventRequest;
import com.microservices.event_service.model.response.EventRegisteredUserDetailsResponse;
import com.microservices.event_service.model.response.EventResponse;
import com.microservices.event_service.service.IEventAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/event")
@RequiredArgsConstructor
public class EventAdminController {

    private final IEventAdminService iEventAdminService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createEvent(@RequestBody EventRequest eventRequest) {
        return iEventAdminService.createEvent(eventRequest);
    }

    @GetMapping("/details")
    public List<Event> getAllEventDetails(){
       return iEventAdminService.getAllEventDetails();
    }

    @GetMapping("/registered/details")
    public List<EventRegisteredUserDetailsResponse> getAllEventRegisteredUserDetails(){
        return iEventAdminService.getAllEventRegisteredUserDetails();
    }

    @GetMapping("/registered/details/{eventId}")
    public EventResponse getAllEventRegisteredUserDetailsByEventId(@PathVariable("eventId") Long eventId){
        return iEventAdminService.getAllEventRegisteredUserDetailsByEventId(eventId);
    }
}
