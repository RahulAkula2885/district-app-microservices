package com.microservices.event_service.service.validations;

import com.microservices.event_service.commons.exceptions.CustomException;
import com.microservices.event_service.entity.EventRegisteredUsers;
import com.microservices.event_service.model.request.EventRegistrationRequest;
import com.microservices.event_service.model.request.EventRequest;
import com.microservices.event_service.model.response.UserResponse;
import com.microservices.event_service.repository.IEventRegisteredUsersRepository;
import com.microservices.event_service.service.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventValidations {

    private final RestTemplate restTemplate;
    private final IEventRegisteredUsersRepository iEventRegisteredUsersRepository;
    private final UserFeignClient userFeignClient;


    public void checkCreateEventValidations(EventRequest eventRequest) {

        if (!StringUtils.hasText(eventRequest.eventName())) {
            throw new CustomException("Event name must not be null or empty");
        }
        if (!StringUtils.hasText(eventRequest.description())) {
            throw new CustomException("Description must not be null or empty");
        }
        if (eventRequest.date() == null) {
            throw new CustomException("Date must not be null");
        }
        if (eventRequest.fromTime() == null) {
            throw new CustomException("From Time must not be null or empty");
        }
        if (eventRequest.toTime() == null) {
            throw new CustomException("To Time must not be null or empty");
        }
        if (eventRequest.fromTime().isAfter(eventRequest.toTime())) {
            throw new CustomException("From Time must be before To Time");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(eventRequest.date().toString(), formatter);
        } catch (DateTimeParseException e) {
            throw new CustomException("Invalid date format. Expected format: yyyy-MM-dd");
        }

    }

    public void checkValidationsForEventRegistration(EventRegistrationRequest request) {
        if (request.eventId() == null || request.eventId() == 0) {
            throw new CustomException("Event id must not be null or 0");
        }
        if (request.userId() == null || request.userId() == 0) {
            throw new CustomException("User id must not be null or 0");
        }

       // UserResponse userResponse = restTemplate.getForObject("http://localhost:9001/v1/app/users/details/" + request.userId(), UserResponse.class);
        UserResponse userResponse = userFeignClient.getUserDetailsByUserId(request.userId());
        if (userResponse == null) {
            throw new CustomException("User details not found");
        }
        System.out.println(userResponse);

        Optional<EventRegisteredUsers> eventRegisteredUsers = iEventRegisteredUsersRepository.findByEventIdAndUserId(request.eventId(), request.userId());

        if (eventRegisteredUsers.isPresent()) {
            throw new CustomException("You've already registered");
        }
    }
}
