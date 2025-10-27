package com.microservices.movie_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.movie_service.commons.CommonResponse;
import com.microservices.movie_service.commons.CommonUtil;
import com.microservices.movie_service.entity.MovieDetails;
import com.microservices.movie_service.entity.TheaterSlots;
import com.microservices.movie_service.entity.Theaters;
import com.microservices.movie_service.model.request.MovieRequest;
import com.microservices.movie_service.repository.IMovieDetailsRepository;
import com.microservices.movie_service.repository.ITheaterSlotRepository;
import com.microservices.movie_service.repository.ITheatersRepository;
import com.microservices.movie_service.service.cache.MoviesCacheService;
import com.microservices.movie_service.service.validations.MovieValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieAdminServiceImpl implements IMovieAdminService {

    private final MovieValidations movieValidations;
    private final MoviesCacheService moviesCacheService;
    private final IMovieDetailsRepository iMovieDetailsRepository;
    private final ITheatersRepository iTheatersRepository;
    private final ITheaterSlotRepository iTheaterSlotRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<CommonResponse> createMovies(MovieRequest request) throws JsonProcessingException {

        movieValidations.checkValidationsForCreateMovies(request);

        MovieDetails movieDetails = MovieDetails.builder()
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .slots(request.getSlots())
                .castAndCrews(objectMapper.writeValueAsString(request.getCastAndCrews()))
                .enabled(true)
                .deleted(false)
                .createdTime(Instant.now())
                .modifiedTime(Instant.now())
                .build();

        moviesCacheService.saveMovie(movieDetails);

        CommonResponse commonResponse = CommonUtil.buildCommonResponse("SUCCESS", movieDetails);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public List<MovieDetails> getAllMovieDetails() {
        return iMovieDetailsRepository.findAll();
    }

    @Transactional
    @Override
    public ResponseEntity<CommonResponse> createTheater(Theaters request) {

        Theaters theaters = Theaters.builder()
                .name(request.getName())
                .description(request.getDescription())
                .theaterSlots(request.getTheaterSlots())
                .enabled(true)
                .deleted(false)
                .createdTime(Instant.now())
                .modifiedTime(Instant.now())
                .build();

        System.out.println(theaters);

        Theaters theatersRes = moviesCacheService.createTheater(theaters);

        CommonResponse commonResponse = CommonUtil.buildCommonResponse("SUCCESS", theatersRes);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @Override
    public List<Theaters> getAllTheaterDetails() {
        List<TheaterSlots> theaterSlots = iTheaterSlotRepository.findAll();
        System.out.println("SLots:- " + theaterSlots);

        return iTheatersRepository.findAll();
    }
}
