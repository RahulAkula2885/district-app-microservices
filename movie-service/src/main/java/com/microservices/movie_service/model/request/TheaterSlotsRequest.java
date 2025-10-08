package com.microservices.movie_service.model.request;

import lombok.Data;

@Data
public class TheaterSlotsRequest {

    private Long theaterId;
    private Long screenId;
    private String slotTime;
    private Long totalSlots;
}
