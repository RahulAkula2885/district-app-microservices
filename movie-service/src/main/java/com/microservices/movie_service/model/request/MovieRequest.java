package com.microservices.movie_service.model.request;

import com.microservices.movie_service.entity.Theaters;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MovieRequest {

    private String title;
    private String shortDescription;
    private String description;
    private Long slots;
    private List<CastAndCrew> castAndCrews = new ArrayList<>();
    //private List<Theaters> theaters = new ArrayList<>();
}
