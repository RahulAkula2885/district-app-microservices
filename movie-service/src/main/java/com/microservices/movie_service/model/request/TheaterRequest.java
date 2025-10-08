package com.microservices.movie_service.model.request;

import com.microservices.movie_service.entity.TheaterSlots;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class TheaterRequest {

    private String name;
    private String description;
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TheaterSlots> theaterSlots;
}
