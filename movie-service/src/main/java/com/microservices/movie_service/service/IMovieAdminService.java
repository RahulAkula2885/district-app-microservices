package com.microservices.movie_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.movie_service.commons.CommonResponse;
import com.microservices.movie_service.entity.MovieDetails;
import com.microservices.movie_service.entity.Theaters;
import com.microservices.movie_service.model.request.MovieRequest;
import com.microservices.movie_service.model.request.TheaterRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMovieAdminService {
    ResponseEntity<CommonResponse> createMovies(MovieRequest request) throws JsonProcessingException;

    List<MovieDetails> getAllMovieDetails();

    ResponseEntity<CommonResponse> createTheater(Theaters request);

    List<Theaters> getAllTheaterDetails();
}
