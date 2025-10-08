package com.microservices.movie_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservices.movie_service.commons.CommonResponse;
import com.microservices.movie_service.entity.MovieDetails;
import com.microservices.movie_service.entity.Theaters;
import com.microservices.movie_service.model.request.MovieRequest;
import com.microservices.movie_service.model.request.TheaterRequest;
import com.microservices.movie_service.service.IMovieAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/movies")
@RequiredArgsConstructor
public class MovieAdminController {

    private final IMovieAdminService iMovieAdminService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createMovies(@RequestBody MovieRequest request) throws JsonProcessingException {
        return iMovieAdminService.createMovies(request);
    }

    @GetMapping("/details")
    public List<MovieDetails> getAllMovieDetails(){
        return iMovieAdminService.getAllMovieDetails();
    }

    @PostMapping("/create/theater")
    public ResponseEntity<CommonResponse> createTheater(@RequestBody Theaters request) throws JsonProcessingException {
        return iMovieAdminService.createTheater(request);
    }

    @GetMapping("/theater/details")
    public List<Theaters> getAllTheaterDetails(){
        return iMovieAdminService.getAllTheaterDetails();
    }
}
