package com.microservices.movie_service.service;

import com.microservices.movie_service.repository.IMovieDetailsRepository;
import com.microservices.movie_service.repository.ITheatersRepository;
import com.microservices.movie_service.service.cache.MoviesCacheService;
import com.microservices.movie_service.service.validations.MovieValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieAppServiceImpl implements IMovieAppService {

    private final MovieValidations movieValidations;
    private final MoviesCacheService moviesCacheService;
    private final IMovieDetailsRepository iMovieDetailsRepository;
    private final ITheatersRepository iTheatersRepository;
}
